<script lang="ts">
    import {Link,link} from 'svelte-navigator';
    export let text = "NOT_SPECIFIED";
    export let to ="/"
    export let iconClass = "bi-alarm rounded app-color-gray"
</script>

<style>
    .nav-drawer-row:hover
    {
       background-color: gray;
       font-size: 1.1em;
       color: white;
    }

    .nav-drawer-row:hover *
    {
        color: inherit !important;
    }
</style>

<div class="d-flex flex-row  justify-content-between align-items-center px-2 py-1 nav-drawer-row rounded">
    <h6 class={iconClass + " text-black"}></h6>
    <a class="fw-bold text-secondary" href={to} use:link>{text}</a>
</div>