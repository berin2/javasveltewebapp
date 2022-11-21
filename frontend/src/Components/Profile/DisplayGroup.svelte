<script lang="ts">
    export let colClass: string = "col-12 col-md-6";
    export let iconClass: string = ""
    export let headerText: string = ""
</script>
<style></style>


<div class={colClass}>
    <section class="d-flex flex-row align-items-center justify-content-between">
        <h3>{headerText}</h3>
        <i class={iconClass}/>
    </section>
    <slot name="body">
        <p>Default Injected body.</p>
    </slot>
</div>