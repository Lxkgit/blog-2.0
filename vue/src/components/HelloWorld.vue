<script setup lang="ts">
import { useStore } from 'vuex';

defineProps<{ msg: string }>()

const store = useStore()

const addCount = () => {
  store.commit('increment')
}

</script>

<template>
  <h1>{{ msg }}</h1>
  <p>vuex test: {{ store.state.count }}</p>
  <button @click="addCount">点我count+1</button>
</template>

<style scoped>
a {
  color: #42b983;
}

label {
  margin: 0 0.5em;
  font-weight: bold;
}

code {
  background-color: #eee;
  padding: 2px 4px;
  border-radius: 4px;
  color: #304455;
}
</style>
