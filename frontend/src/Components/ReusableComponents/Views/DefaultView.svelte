<script lang="ts">
    import {fade} from "svelte/transition";
    export let viewTitle:string  = "View Title"
    export let iconClass:string = " bi bi-emoji-smile "
</script>
<style>

</style>

<section class="app-container  container-fluid" in:fade={{duration:500}}>
    <div class="row justify-content-center">
        <section class="shadow rounded col-md-8 col-sm-10 app-container-border-gray app-container-col bg-white d-flex flex-row justify-content-center align-items-center">
            <section class="w-100 h-100 container-fluid">
                <section class="row d-flex flex-row  justify-content-between  align-items-center p-3 border-bottom">
                    <h3 class=" col-8 text-secondary">
                        <slot name="headingText">{viewTitle}</slot>
                    </h3>
                    <h3 class={iconClass + " text-secondary col-2 "}>
                    </h3>
                </section>
                <section class=" row d-flex flex-row justify-content-between  align-items-center p-3 ">
                    <slot name="body">
                        <h1 class="col-12 text-secondary">Default Body Content</h1>
                    </slot>
                </section>
            </section>
        </section>
    </div>
</section>