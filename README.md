# oEmbed JSON data 생성 

url 입력 시 youtube, vimeo, twitter 등의 콘텐츠를 embed로 변환하여 보여줌


1. stack
	* java: 1.8
	* Spring Boot: 2.7.10
		* Library: Lombok, commons-validator, json-simple
	* Vue.js: Vue Cli, ESLint, Bootstrap, axios
	* Visual Studio Code
	
2. 작업 설명
	1. Backend
	* 브라우저에서 URL 주소를 보내면 입력값 유효성 체크
	* https://provider.json 페이지로 연결하여 provider 데이터 불러옴
	* provider 데이터 중, endpoints의 url 정보만 list로 생성
	* 입력받은 url 주소로 host를 추출하여, list 내용중에 host 이름을 포함하고 있는 url 리턴하여 oEmbed url생성
	* 생성된 oEmbed url을 json 객체로 생성하여 리턴하는 작업
	* 에러 발생한 경우에는 별도의 Exception 호출하여 클라이언트로 message 리턴
	
	
	2. Frontend
	* input에서 url validation 확인
		* instagram의 경우, 서비스 준비중으로 alert 표시
	* axios를 사용하여 get 형태로 비동기 통신 구현
	* youtube provide를 기준으로 데이터 형식 구현
		* 데이터가 null이거나 undefined인 경우에는 표시하지 않도록 설정