<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
   
      Name:
      <input type="text" v-model="youname" name="name" ><br>
      <input type="submit" value="发送" @click="hello">
      <input type="reset" value="重置">
    
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data() {
    return {youname:'lilei'}},
  methods: {
    hello() {
      axios
          .get('/v1/demo/hello/'+this.youname)
          .then(response => (this.msg = response.data))
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
