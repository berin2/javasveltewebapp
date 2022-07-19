function EmailValidator(val)
{
    let regex = /[^\s]+@\.com|net|org/;
    return regex.test(val);
}

export default EmailValidator;