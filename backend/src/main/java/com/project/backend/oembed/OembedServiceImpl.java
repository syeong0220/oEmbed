package com.project.backend.oembed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("OembedService")
public class OembedServiceImpl implements OembedService {
    private static final Logger log = LoggerFactory.getLogger(OembedController.class);

    @Override
    public JSONObject getOembedList(String url) throws Exception {
        // 1. URL 유효성 검증되면 실행
        if (isValidUrl(url)) {
            // 2. https://oembed.com/providers.json 데이터 가져오기
            JSONArray data = this.getProvidersData();
            // 3. provider.json 데이터 중, endPoints의 url(oembed) url만 list로 생성
            List<String> list = this.getEndPointsList(data);
            // 4. oEmbed URL 만들기
            String createUrl = this.createUrl(url, list);
            // 5. 생성된 URL httpClient
            JSONObject result = this.returnUrl(createUrl);
            return result;
        } else {
            log.error("oEmbed 생성에 실패하였습니다. : {}", url);
            return null;
        }
    }

    public boolean isValidUrl(String url) throws Exception {
        UrlValidator urlValidator = new UrlValidator();

        if (!urlValidator.isValid(url)) {
            log.error("Not Valid URL : {}", url);
            throw new Exception("유효하지 않은 URL 입니다.");
        }
        log.info("Valid URL : {}", url);
        return urlValidator.isValid(url);
    }

    public String getHost(String originUrl) throws Exception {
        String host = null;
        URL url = new URL(originUrl);
        host = url.getHost();

        if (host == null) {
            throw new Exception("주소가 확실하지 않습니다.");
        }
        return host;
    }

    public JSONArray getProvidersData() throws IOException, ParseException {
        String jsonUrl = "https://oembed.com/providers.json";
        InputStream is = new URL(jsonUrl).openStream();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();

            int rt;
            while ((rt = br.read()) != -1) {
                sb.append((char) rt);
            }
            String jsonText = sb.toString();
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonText);

            return jsonArray;
        } finally {
            is.close();
        }
    }

    public List<String> getEndPointsList(JSONArray arrayData) {
        List<String> list = new ArrayList<String>();
        try {
            for (int i = 0; i < arrayData.size(); i++) {
                // 1. Oembed 목록의 Object 정보 가져오기
                JSONObject providersInfo = (JSONObject) arrayData.get(i);

                // 2. endpoints 정보 추출
                JSONArray endPoints = (JSONArray) providersInfo.get("endpoints");

                // 3. 서비스별 첫번째 endPoints의 url 가져오기
                JSONObject urlData = (JSONObject) endPoints.get(0);
                String urlList = (String) urlData.get("url");

                // 4. oembed url만 담긴 list 생성
                list.add(urlList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String createUrl(String originUrl, List<String> list) throws Exception {
        String oEmbedUrl = "";
        String concatUrl = "?url=" + originUrl;

        // 1. host 정보 가져오기
        String host = this.getHost(originUrl);

        // 2. list에서 host이름을 포함한 oEmbed url 찾기
        for (String str : list) {
            if (str.contains(host)) {
                if (str.contains("oembed.")) {
                    // host이름에 oembed.이 포함돼있는지 확인
                    if (str.contains("{format}")) { // host이름에 oembed.이 포함돼있으면 {format}이 있는지 확인
                        str = str.replace("{format}", "json"); // {format} => json으로 변경
                    }
                    oEmbedUrl = str.concat(concatUrl);
                } else if (str.contains("_oembed")) { // host이름에 _oembed가 포함돼있는지 확인
                    // 인스타그램 처리
                } else {
                    oEmbedUrl = str.concat(concatUrl) + "&format=json";
                }
                break;
            }
        }
        return oEmbedUrl;
    }

    public JSONObject returnUrl(String createUrl) throws ClientProtocolException, IOException, ParseException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // get 메서드와 URL 설정
        HttpGet httpGet = new HttpGet(createUrl);
        httpGet.addHeader("Content-Type", "application/json");

        // get 요청
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        String responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(responseStr);

        JSONObject resultObj = (JSONObject) obj;

        return resultObj;
    }

}
