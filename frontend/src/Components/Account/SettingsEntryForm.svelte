<script lang="ts">
    import {throwError} from "svelte-preprocess/dist/modules/errors";
    import BaseButton from "../ReusableComponents/Buttons/BaseButton.svelte";
    import TextInput from "../ReusableComponents/FormControls/TextInput.svelte";
    import {isFalsy, isFalsyStore} from "../../Validators/IsFalsyObjectValidator";
    import {fade, fly} from "svelte/transition";
    import PasswordInput from "../ReusableComponents/FormControls/PasswordInput.svelte";
    import RadioInputProp from "../../PropObjects/RadioInputProp";
    import RadioGroup from "../ReusableComponents/FormControls/RadioGroup.svelte";
    import ThemeClass from "../ReusableComponents/ThemeClasses/ThemeClass";
    import FoeList from "./FoeList.svelte";
    import PhoneNumberInput from "../ReusableComponents/FormControls/PhoneNumberInput.svelte";
    import PhoneNumberForm from "./PhoneNumberForm.svelte";
    import {PhoneViewTypeEnum, SettingsEntryFormSubViewEnum} from "../../Datastructures/EnumClasses";
    import {afterUpdate, onMount} from "svelte";
    import {DELETE, HttpClient, HttpRequest, PUT} from "../../Services/HttpClients/HttpClient";
    import {httpClient} from "../../Stores/HttpClient";
    import ApiService from "../../Services/ApiService/ApiService";
    import SvelteUpUserProfileDto from "../../Dto/profile/SvelteUpUserProfileDto";
    import DeleteAccountDto from "../../Dto/profile/DeleteAccountDto";
    import ContactDto from "../Contacts/Dto/ContactDto";
    import accountStore from "./store/AccountStore";
    import PasswordDto from "../dto/PasswordDto";
    import ValidatorMessage from "../../Validators/ValidatorMessage.svelte";

    export let formTitle: string = "Default Title";
    export let formTitleIconClass: string | null = null;
    export let formType: SettingsEntryFormSubViewEnum | null = null;
    let toggleVerification: boolean = false;

    export let dataType: string = "";
    export let dispatchButtonText: string = "Submit";
    export let paragraphText: string = ""
    export let currentValueToShow: string | null = null;
    export let transitionBackFunction: () => void = () => throwError("Expecting parent view to pass dispatch function to settings form, but the parent did not pass  a transitionBackFunction prop.");

    let value: any = "";
    let confirmValue: string = "";
    let currentValueToValidateAgainst: string = "";

    let areaCode: number = 0;
    let phoneNUmber: number = 0;

    let foesToRemove: ContactDto [] = [];

    let foeList: ContactDto [] = isFalsyStore(accountStore, $accountStore) && isF ? $accountStore.foeList : [];
    let messageToRender: string = "";


    const SUCCESS: string = "Success!";
    const ERROR: string = "Operation failed.Try again.";
    const client: HttpClient = $httpClient;


    let accountRadioProps: RadioInputProp [] = [
        new RadioInputProp(0, " col-12 ", true, " Deactivate Account ", ""),
        new RadioInputProp(1, " col-12 ", false, " Delete Account ", "")
    ];
    let twoFactorAuthenticationProps: RadioInputProp [] = [
        new RadioInputProp(0, " col-12 ", true, " Enable Two Factor Authentication ", ""),
        new RadioInputProp(1, " col-12 ", false, " Disable Two Factor Authentication ", "")
    ];
    let radioGroup: boolean = false;

    let submitFunction: () => void = () => {
    };
    let validatorFunction: () => boolean  = () => {
        return (isFalsy(value) || confirmValue != value)
    };

    let disabled: boolean|null = null;

    function updateCheck(): void {
        disabled = validatorFunction();
    }

    onMount(() => {
        let dataDto: SvelteUpUserProfileDto = new SvelteUpUserProfileDto();

        function onSuccess() {
            messageToRender = SUCCESS;
        }

        function onFail() {
            messageToRender = ERROR;
        }

        switch (formType) {
            case SettingsEntryFormSubViewEnum.ACCOUNT_DELETION:
                submitFunction = () => {
                    let deleteAccountRequest: DeleteAccountDto = new DeleteAccountDto();
                    let request: HttpRequest = new HttpRequest(DELETE, ApiService.getAccountDeleteUrl(), true, ApiService.getDefaultHeaders(), deleteAccountRequest);
                    client.dispatchRequest(request, onSuccess, onFail)
                }
                break;
            case SettingsEntryFormSubViewEnum.EMAIL:
                submitFunction = () => {
                    dataDto.email = value;
                    let request: HttpRequest = new HttpRequest(DELETE, ApiService.getAccountDeleteUrl(), true, ApiService.getDefaultHeaders(), dataDto);
                    client.dispatchRequest(request, onSuccess, onFail);
                }
                break;
            case SettingsEntryFormSubViewEnum.FOE_LIST:
                submitFunction = () => {
                    dataDto.email = value;
                    let request: HttpRequest = new HttpRequest(DELETE, ApiService.getFoeListUrl(), true, ApiService.getDefaultHeaders(), dataDto);
                    client.dispatchRequest(request, onSuccess, onFail);
                }
                break;
            case SettingsEntryFormSubViewEnum.PASSWORD:

                submitFunction = () => {
                    let passwordDto: PasswordDto = new PasswordDto(currentValueToValidateAgainst, value);
                    let request: HttpRequest = new HttpRequest(PUT, ApiService.getPasswordUrl(), true, ApiService.getDefaultHeaders(), passwordDto);
                    client.dispatchRequest(request, onSuccess, onFail);
                }
                break;
            case SettingsEntryFormSubViewEnum.PHONE:
                break;
        }
    })
    afterUpdate(() => updateCheck())


</script>
<style>
    .setting-col {
        background-color: white;
        min-height: 35vh;
        max-height: 75vh;
    }
</style>

<section class="app-margin container-fluid">
    <section class="row d-flex flex-row justify-content-center align-items-center ">
        <div class="col-lg-3 col-md-4 col-sm-8 py-3" in:fly={{x:1000}}>
            <section class="col-12 d-flex flex-row justify-content-between">
                <h1 class="text-black">Bakk</h1>
                <div class="form-group">
                    <button class=" btn btn-outline-success bi bi-arrow-left-circle" on:click|preventDefault={transitionBackFunction}></button>
                </div>
            </section>
            <div class="setting-col shadow d-flex flex-column justify-content-between align-items-center rounded border ">
                <section class="d-flex flex-row justify-content-between w-100 p-3 border-bottom">
                    <h3>{formTitle}</h3>
                    {#if !isFalsy(formTitleIconClass)}
                        <h3 class={formTitleIconClass + " text-secondary "}></h3>
                    {/if}
                </section>

                <p class="text-muted text-center p-3 ">{paragraphText}</p>
                {#if formType == SettingsEntryFormSubViewEnum.EMAIL}
                    <TextInput labelValue={dataType} bind:textValue={value} iconValue=" bi bi-check "/>
                    <TextInput  bind:textValue={confirmValue} labelValue={" Confirm " + dataType} iconValue=" bi bi-check-all "/>
                {:else if formType == SettingsEntryFormSubViewEnum.PASSWORD}
                    <PasswordInput labelValue="Current Password" bind:passwordValue={currentValueToValidateAgainst} iconValue=" bi bi-check "/>
                    <PasswordInput labelValue={dataType}  bind:passwordValue={value} iconValue=" bi bi-check "/>
                    <PasswordInput  bind:passwordValue={confirmValue} labelValue={" Confirm " + dataType} iconValue=" bi bi-check-all "/>
                {:else if formType == SettingsEntryFormSubViewEnum.ACCOUNT_DELETION}
                    <RadioGroup radios={accountRadioProps} radioGroup = {radioGroup}/>
                {:else if formType == SettingsEntryFormSubViewEnum.FOE_LIST}
                    <FoeList blockedFoes={foeList} removalList={foesToRemove} />
                {:else if formType == SettingsEntryFormSubViewEnum.PHONE}
                    <PhoneNumberForm viewType={PhoneViewTypeEnum.RENDER_BOTH} bind:togglVerificationView={toggleVerification} />
                {:else if formType == SettingsEntryFormSubViewEnum.TWO_FACTOR_AUTHENTICATION}
                    <RadioGroup radios={twoFactorAuthenticationProps} radioGroup = {radioGroup}/>
                {/if}
                <div>{messageToRender}</div>
                <div class="p-3">
                    <BaseButton  color={ThemeClass.app_control_green} value={dispatchButtonText} onClick={submitFunction} disabled={disabled}/>
                </div>
        </div>
        </div>
    </section>

</section>