<template>
    <div class="play">
        <h1>芜湖起飞</h1>
    </div>
</template>

<script>
export default {
  name: 'Play',
  data () {
    return {
      play: {
        show: false
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.play {
  z-index: 999;
  width: 200px;
  position: fixed;
  bottom: 100px;
  left: 50%;
  background-color: red;
  padding: 30px 10px;
  transform: translate(-50%, 0%);
}

</style>
