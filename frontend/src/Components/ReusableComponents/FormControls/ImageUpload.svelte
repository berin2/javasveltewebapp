<script lang="ts">
    import FileUpload from "./FileUpload.svelte";
    import CircularImage from "../LayoutComponents/ImageComponentReusables/CircularImage.svelte";

    export let src: string | null = null;

</script>
<style></style>

<section>
    <CircularImage />
    <FileUpload />
</section>