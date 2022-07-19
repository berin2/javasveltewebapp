<script lang="ts">
    import BaseButton from "../Buttons/BaseButton.svelte";
    export let modalId: string = "#" + (Math.random() * Math.random() * Math.random()).toString() + "-app-modal";
</script>
<style>
    .app-modal
    {
        height: 100vh !important;
        width: 100vw  !important;
    }
</style>

    <section class="modal app-modal" id={modalId}>
        <div class="modal-dialog modal-lg">
                <div class="modal-content">
                        <slot name="modal-content">
                                    <div class="bg-white">
                                        No content injected into modal!
                                    </div>
                        </slot>
                    </div>
            </div>
    </section>
