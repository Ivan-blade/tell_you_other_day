<template>
    <div id="editor">
        <mavon-editor 
            :toolbarsFlag=decision
            defaultOpen="edit"
            placeholder=" "
        ></mavon-editor>
    </div>
</template>
<script>
// Local Registration
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
    name: 'editor',
    components: {
        mavonEditor
        // or 'mavon-editor': mavonEditor
    },
    data() {
      return {
        decision: false
      }
    },
}
</script>
<style>
#editor {
    margin: auto;
    width: 100%;
}
</style>