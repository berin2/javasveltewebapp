<script>
    import { createPopperActions } from 'svelte-popperjs';
    const [popperRef, popperContent] = createPopperActions();
    const popperOptions = {
        modifiers: [
            { name: 'offset', options: { offset: [0, 8] } }
        ],
    };

    let showTooltip = false;
    $: toolTip = showTooltip && true;
</script>

<button
        use:popperRef
        on:mouseenter={() => showTooltip = true}
        on:mouseleave={() => {showTooltip = false;}}
>
    My button
</button>
{#if toolTip}
    <div id="tooltip" class="bg-white border" use:popperContent={popperOptions}>
        My tooltip
        <div id="arrow" data-popper-arrow ></div>
    </div>
    {:else}
    <div>hi!</div>
{/if}
