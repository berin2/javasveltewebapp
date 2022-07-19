class DeleteAccountDto {
    public deleteAccount: boolean;
    public deactivateAccount: boolean;

    public constructor(deleteAccountRequest:boolean) {
        this.deleteAccount = false;
        this.deactivateAccount = false;
        deleteAccountRequest ? this.deleteAccount = true : this.deactivateAccount = true;
    }
}

export default DeleteAccountDto;