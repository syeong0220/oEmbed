<template>
  <section class="container">
    <div class="div-search">
      <h2>oEmbed Test</h2> 
      <div class="div-input">
        <input type="text" v-model="form.url" placeholder="Enter oEmbed Url" v-on:keyup.enter="submit"/>
        <button @click="submit">확인</button>
      </div>
    </div>
    <div class="div-table" style="">
      <table class="table b-table table-striped">
        <tbody class="rowclass">
          <!-- eslint-disable vue/no-use-v-if-with-v-for,vue/no-confusing-v-for-v-if -->
          <tr v-for="(value, text) in data" v-if="value !== undefined && value !== null" :key="text">
            <td v-if="text === 'html' || text === 'thumbnail_url'">{{ text }}<br><div>{{ isSize(text) }}</div></td>
            <td v-else>{{ text }}</td>
            <td v-if="text === 'title'" style="font-weight: bold;">{{ value }}</td>
            <td v-else-if="text === 'html'">{{value}}<div v-html="value"></div></td>
            <td v-else-if="text === 'thumbnail_url'">
              <a :href="value">{{ value }}</a>
              <br><img :src="value" />
            </td>
            <td v-else-if="isUrl(text)">
              <a :href="value">{{ value }}</a>
            </td>
            <td v-else>{{ value }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script>
import axios from 'axios'
export default {
  name: 'oembed',
  data() {
    return {
      form: {
        url: ''
      },
      data: {}
    }
  },
  methods: {
    submit() {
      if (this.form.url !== '') {
        if(this.form.url.includes('instagram') === false){
          axios.get('/api/oembed/list?url=' + this.form.url, {
          }).then(res => {
            // this.$set(this, 'data', res.data)
            this.$set(this.data, 'title', res.data.title)
            this.$set(this.data, 'type', res.data.type)
            this.$set(this.data, 'version', res.data.version)
            this.$set(this.data, 'provider_name', res.data.provider_name)
            this.$set(this.data, 'provider_url', res.data.provider_url)
            this.$set(this.data, 'author_name', res.data.author_name)
            this.$set(this.data, 'author_url', res.data.author_url)
            this.$set(this.data, 'is_plus', res.data.is_plus)
            this.$set(this.data, 'html', res.data.html)
            this.$set(this.data, 'width', res.data.width)
            this.$set(this.data, 'height', res.data.height)
            this.$set(this.data, 'duration', res.data.duration)
            this.$set(this.data, 'description', res.data.description)
            this.$set(this.data, 'thumbnail_url', res.data.thumbnail_url)
            this.$set(this.data, 'thumbnail_width', res.data.thumbnail_width)
            this.$set(this.data, 'thumbnail_height', res.data.thumbnail_height)
            this.$set(this.data, 'thumbnail_url_with_play_button', res.data.thumbnail_url_with_play_button)
            this.$set(this.data, 'upload_date', res.data.upload_date)
            this.$set(this.data, 'video_id', res.data.video_id)
            this.$set(this.data, 'uri', res.data.uri)
            this.$set(this.data, 'cache_age', res.data.cache_age)
          }).catch((e) => {
            alert(e.response.data.message)
            this.$set(this, 'data', {})
            this.$set(this.form, 'url', '')
          })
        } else {
          alert('instagram은 oEmbed 서비스 준비 중에 있습니다.')
          return false
        }
      } else {
        alert('url을 입력해 주세요')
        return false
      }
    },
    isUrl(text) {
      return text.includes("url");
    },
    isSize(text) {
      const width = this.data.width
      const heigth = this.data.height
      const thumbnailWidth = this.data.thumbnail_width
      const thumbnailHeight = this.data.thumbnail_height

      const widthSize = '(' + width + ')'

      const htmlSize = this.heigth !== undefined ? '(' + width + '/' + heigth + ')' : widthSize
      const thumbnailSize = '(' + thumbnailWidth + '/' + thumbnailHeight + ')'
      return text === 'html' ? htmlSize : thumbnailSize
    }
  }
}
</script>

<style scoped>
.div-search{
  text-align:center;
  margin-top:40px;
  background-color: #46aaaa;
}
h2{
  color:white;
  padding:20px;
  font-weight: bold;
}
.div-input{
  text-align:center;
}
.div-input > input{
   margin-bottom: 20px; 
   width:60%;
   border:none;
   padding: 15px 15px;

}
.div-input > button{
    cursor: pointer;
    position: absolute;
    top: 136px;
    right: 436px;
    border: none;
    background: #eed243;
    font-weight: bold;
    width: 60px;
    height: 35px;
    font-size: 13px;
}
.div-table{
  border: 15px solid #e2e3e5;
}
table{
 border: 15px solid #fff;
}
td{
  border-bottom:none;
}
</style>
