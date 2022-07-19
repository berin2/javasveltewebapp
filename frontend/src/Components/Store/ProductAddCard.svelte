<script lang="ts">
    import {navigate} from "svelte-navigator";

    function toProductAdd():void
    {
        navigate("productadd");
    }
</script>
<style>
    .product-card {
        width: 400px;
    }
</style>

    <section class="d-flex flex-column align-items-center justify-content-center float-end">
        <h5 class="col-12 text-center text-secondary">Add a new product</h5>
            <button class="btn btn-outline-success" on:click|preventDefault = {() => toProductAdd()}>
                <i class="bi bi-plus-circle"></i>
            </button>
    </section>
