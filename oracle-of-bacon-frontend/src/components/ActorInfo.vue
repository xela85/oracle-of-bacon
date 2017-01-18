<template>
  <div class="actor-box" ref="infobox">
    <h3>Actor info</h3>
    <div class="scrollable">
      <img :src="actor.image"/>
      <h4>{{actor.name}}</h4>
      <p>{{actor.birth_date}}</p>
      <p>{{actor.description}}</p>
      <ul>
          <li v-for="hobby in actor.occupation">{{hobby}}</li>
      </ul>
      <p>{{actor.error}}</p>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'actor-info',
    props: {
      actorName: String,
    },
    watch: {
      actorName(newName) {
        if (newName) {
          this.$refs.infobox.style.visibility = 'visible';
          fetch(`/api/actor?name=${newName}`)
            .then(response => response.json())
            .then((bodyJson) => {
              this.actor = bodyJson;
              const y = this.actor.birth_date.split('-');
              this.actor.birth_date = `${y[2]}/${y[1]}/${y[0]}`;
            });
        } else {
          this.$refs.infobox.style.visibility = 'hidden';
        }
      },
    },
    data() {
      return {
        actor: [],
      };
    },
  };
</script>
<style scoped>

  .actor-box {
    text-align: center;
  }

  .scrollable {
    overflow-y: scroll;
    height: 90%;
  }

</style>
