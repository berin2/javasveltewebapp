<script lang="ts">
    export let onChange: any = () => alert("form control not implemented.");
    export let col:string = "";
    const CONTROL_ID: string = (Math.random() * Math.random() * Math.random()).toString();

    export let labelValue: string = "";
    export let placeholderValue: string = "";
    export let spanValue: string = "";
    export let imageValue: any = "";
    export let iconValue: string = "";
    export let validator: (arg:any) => boolean  = () => {return true};
    export let image:any|null = null;

    const reader:FileReader =  new FileReader();

    function readImageIntoSvelteComponent(e:Event):void
    {
        let imageFile:File = e.target.files[0];
        reader.readAsDataURL(imageFile);
        reader.onload = (e) => {
            image = e.target.result;
            onChange(image);
        }
    }

    function onChangeWrapper(e:Event)
    {
        readImageIntoSvelteComponent(e);
    }

</script>

<style>

</style>

<section class={`form-group d-flex flex-column align-items-start ${col}}`}>
    <label for={CONTROL_ID} class=" w-100 form-label d-flex flex-row justify-content-between ps-1">{labelValue} <span class={iconValue + " ms-auto"}></span> </label>
    <input type="file" on:change={(e) => onChangeWrapper(e)} bind:value={imageValue} id={CONTROL_ID} class="form-control" placeholder={placeholderValue}/>
    <span class="form-text text-muted ps-1">{spanValue}</span>
</section>