<template>
    <div class="oembed">
        <OembedComponent />
    </div>
</template>
<script>
import OembedComponent from "@/components/OembedComponent.vue";
export default {
    name: "oembed",
    components: {
        OembedComponent,
    },
};
</script>