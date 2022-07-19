<script lang="ts">
    import DefaultView from "../ReusableComponents/Views/DefaultView.svelte";
    import Graph from "../../Datastructures/Graph";
    import SettingsRoute from "./SettingsRoute.svelte";
    import SettingsEntryForm from "./SettingsEntryForm.svelte";
    import {fade} from "svelte/transition";
    import {SettingsEntryFormSubViewEnum} from "../../Datastructures/EnumClasses";
    import {httpClient} from "../../Stores/HttpClient";
    import ApiService from "../../Services/ApiService/ApiService";


    const EMAIL_TEXT: string =  "Enter and confirm the new email you'd like to associate your account with. Whenever opening emails from SvelteUp, make sure the origin email address is always from @svelteup.app";
    const PASSWORD_TEXT: string = "Enter your old and new password to change your password.";
    const DELETE_TEXT: string = "You can deactivate you account if you want to stay hidden or inactive. Deleting you account will wipe it permanantly. We'll  give you a week to change your mind."
    const BLOCK_TEXT: string = "This is a list of all the people on your block list. People on your block list can't communicate with you.";
    const PHONE_TEXT: string =  "To Change your phone Number we'll need you enter the new code texted to that number.";
    //init possible view transitions
    let viewGraph: Graph<SettingsEntryFormSubViewEnum> = new Graph<SettingsEntryFormSubViewEnum>(SettingsEntryFormSubViewEnum.TWO_FACTOR_AUTHENTICATION);
    viewGraph.createBiDirectionalEdge(SettingsEntryFormSubViewEnum.MENU, SettingsEntryFormSubViewEnum.EMAIL);
    viewGraph.createBiDirectionalEdge(SettingsEntryFormSubViewEnum.MENU, SettingsEntryFormSubViewEnum.PASSWORD);
    viewGraph.createBiDirectionalEdge(SettingsEntryFormSubViewEnum.MENU, SettingsEntryFormSubViewEnum.ACCOUNT_DELETION);
    viewGraph.createBiDirectionalEdge(SettingsEntryFormSubViewEnum.MENU, SettingsEntryFormSubViewEnum.FOE_LIST);
    viewGraph.createBiDirectionalEdge(SettingsEntryFormSubViewEnum.MENU, SettingsEntryFormSubViewEnum.PHONE);
    viewGraph.createBiDirectionalEdge(SettingsEntryFormSubViewEnum.MENU, SettingsEntryFormSubViewEnum.TWO_FACTOR_AUTHENTICATION);
    viewGraph.currentVertex = SettingsEntryFormSubViewEnum.MENU

    $:viewGraphCurrentVertex = viewGraph.currentVertex;

    function transitionBack() {viewGraph =viewGraph.transition(SettingsEntryFormSubViewEnum.MENU);}

    function getDispatchFunction(viewType: SettingsEntryFormSubViewEnum) : () => void
    {
        let returnFunction:() => void = () => {};

        switch(viewType)
        {
            case SettingsEntryFormSubViewEnum.PHONE:
                break;
            case SettingsEntryFormSubViewEnum.FOE_LIST:
                break;
            case SettingsEntryFormSubViewEnum.EMAIL:
                break;
            case SettingsEntryFormSubViewEnum.ACCOUNT_DELETION:
                break;
            case SettingsEntryFormSubViewEnum.ACCOUNT_DELETION:
                break;
        }

        return returnFunction;

    }


</script>
<style></style>

{#if viewGraphCurrentVertex == SettingsEntryFormSubViewEnum.MENU}
    <DefaultView viewTitle="Account Settings" iconClass=" bi bi-gear-wide ">
            <svelte:fragment slot="body">
                <SettingsRoute routeTitle="Email" transitionFunction={() => viewGraph=viewGraph.transition(SettingsEntryFormSubViewEnum.EMAIL)} />
                <SettingsRoute routeTitle="Password" transitionFunction={() => viewGraph=viewGraph.transition(SettingsEntryFormSubViewEnum.PASSWORD)}/>
                <SettingsRoute routeTitle="Account Deletion" transitionFunction={() => viewGraph=viewGraph.transition(SettingsEntryFormSubViewEnum.ACCOUNT_DELETION)} />
                <SettingsRoute routeTitle="Block List" transitionFunction={() => viewGraph=viewGraph.transition(SettingsEntryFormSubViewEnum.FOE_LIST)}/>
                <SettingsRoute routeTitle="Phone" transitionFunction={() => viewGraph=viewGraph.transition(SettingsEntryFormSubViewEnum.PHONE)} />
                <SettingsRoute routeTitle="Two Factor Authentication" transitionFunction={() => viewGraph=viewGraph.transition(SettingsEntryFormSubViewEnum.TWO_FACTOR_AUTHENTICATION)}/>
            </svelte:fragment>
     </DefaultView>
    {:else if viewGraphCurrentVertex == SettingsEntryFormSubViewEnum.EMAIL}
        <SettingsEntryForm formType={SettingsEntryFormSubViewEnum.EMAIL} dataType="New Email" paragraphText={EMAIL_TEXT} formTitleIconClass=" bi bi-envelope " formTitle="Email" transitionBackFunction={transitionBack} dispatchButtonText={"Update Email"} dispatchFunction={()=>{}}/>
    {:else if viewGraphCurrentVertex == SettingsEntryFormSubViewEnum.PASSWORD }
        <SettingsEntryForm formType={SettingsEntryFormSubViewEnum.PASSWORD} dataType="New Password" paragraphText={PASSWORD_TEXT} formTitleIconClass=" bi bi-shield-lock " formTitle="Password" transitionBackFunction={transitionBack} dispatchButtonText={"Update Password"} dispatchFunction={()=>{}}/>
    {:else if viewGraphCurrentVertex == SettingsEntryFormSubViewEnum.ACCOUNT_DELETION}
        <SettingsEntryForm formType={SettingsEntryFormSubViewEnum.ACCOUNT_DELETION} dataType="" paragraphText={DELETE_TEXT} formTitleIconClass=" bi bi-emoji-frown " formTitle="Account Delete" transitionBackFunction={transitionBack} dispatchButtonText={"Toggle"} dispatchFunction={()=>{}}/>
    {:else if viewGraphCurrentVertex == SettingsEntryFormSubViewEnum.FOE_LIST}
        <SettingsEntryForm formType={SettingsEntryFormSubViewEnum.FOE_LIST} dataType="" paragraphText={BLOCK_TEXT} formTitleIconClass=" bi bi-slash-circle " formTitle="Blocked" transitionBackFunction={transitionBack} dispatchButtonText={"Unblock Checked"} dispatchFunction={()=>{}}/>
    {:else if viewGraphCurrentVertex == SettingsEntryFormSubViewEnum.PHONE}
        <SettingsEntryForm formType={SettingsEntryFormSubViewEnum.PHONE} dataType="" paragraphText={PHONE_TEXT} formTitleIconClass=" bi bi-telephone-outbound " formTitle="Phone" transitionBackFunction={transitionBack} dispatchButtonText={"Submit"} dispatchFunction={()=>{}}/>
    {:else if viewGraphCurrentVertex == SettingsEntryFormSubViewEnum.TWO_FACTOR_AUTHENTICATION}
        <SettingsEntryForm formType={SettingsEntryFormSubViewEnum.TWO_FACTOR_AUTHENTICATION} dataType="" paragraphText={PHONE_TEXT} formTitleIconClass=" bi bi-telephone-outbound " formTitle="2X Auth" transitionBackFunction={transitionBack} dispatchButtonText={"Submit"} dispatchFunction={()=>{}}/>
{:else }
{/if}