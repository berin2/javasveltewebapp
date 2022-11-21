<script lang="ts">
    import FileUpload from "../../FormControls/FileUpload.svelte";
    import CircularImage from "./CircularImage.svelte";
    export let height:number = 100;
</script>
<style></style>

<section>
    <CircularImage height={800}/>
    <FileUpload  labelValue="Upload your updated profile image" spanValue="Max Size 1.5MB">

    </FileUpload>
</section>
