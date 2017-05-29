package com.epam.payments.actions.api;

/**
 * {@code ActionConstants} stores all constants
 * that are needed in the Actions.
 *
 * @author Aleksey Tsoy
 */
public final class ActionConstants {
    private ActionConstants(){}

    public static final String SIGN_IN_PAGE                     = "sign-in";
    public static final String SIGN_UP_PAGE                     = "sign-up";
    public static final String PHONE_NUMBER                     = "phone_number";
    public static final String NAME                             = "name";
    public static final String DATE_OF_BIRTH                    = "date_of_birth";
    public static final String PASSWORD                         = "password";
    public static final String CONFIRM_PASSWORD                 = "confirm_password";
    public static final String NUMBER_ERROR                     = "phone_number_error";
    public static final String NUMBER_ERROR_MSG                 = "busy";
    public static final String PASSWORD_ERROR                   = "password_error";
    public static final String BALANCE_FIELD                    = "balance_field";
    public static final String TOP_UP_THE_BALANCE_ERROR         = "topup_error";
    public static final String USER_ID                          = "user_id";
    public static final String USER_ROLE                        = "role";
    public static final String USER_REGION                      = "region";
    public static final String USER_ERROR                       = "user_error";
    public static final String USER_BALANCE                     = "balance";
    public static final String USER_NUMBER                      = "number";
    public static final String PASSWORD_RECOVERY_PAGE           = "passwordrecovery";
    //Headers
    public static final String LANG                             = "lang";
    public static final String REFERER                          = "referer";
    public static final String CONTENT_TYPE_IMAGE               = "image/jpeg";
    public static final String LOGO                             = "logo";
    //Regex
    public static final String VALIDATION_PROPERTIES            = "validation.properties";
    public static final String PASSWORD_REGEX                   = "password.regex";
    public static final String DATE_OF_BIRTH_REGEX              = "date_of_birth.regex";
    public static final String PHONE_NUMBER_REGEX               = "phone_number.regex";
    public static final String NAME_REGEX                       = "name.regex";
    //Management
    public static final String ERROR                            = "error";
    public static final String MAIN_PAGE                        = "main";
    public static final String PAYMENTS_PAGE                    = "payments";
    public static final String SUCCESS                          = "success";
    public static final String DATE_OF_PAYMENT                  = "payment_date";
    public static final String NO_DATA                          = "no_data";
    public static final String BALANCE_ERROR                    = "balance_error";
    public static final String NUMBER                           = "number";
    public static final String SUM                              = "sum";
    public static final String ID                               = "id";
    public static final String PROVIDER                         = "provider";
    public static final String PROVIDER_PAGE                    = "provider";
    public static final String EDIT_PROVIDER_PAGE               = "edit-provider";
    public static final String EDIT_PROVIDERS_PAGE              = "edit-providers";
    public static final String PROVIDERS_REGIONS                = "providers_regions";
    public static final String PROVIDER_ERROR                   = "provider_error";
    public static final String PROVIDER_EDIT_ERROR              = "provider_edit_error";
    public static final String PROVIDER_ALREADY_EXIST           = "provider_already_exist";
    public static final String PROVIDER_NAME                    = "provider_name";
    public static final String PROVIDER_ID                      = "provider_id";
    public static final String PROVIDERS_LOGOTYPE               = "logotype";
    public static final String NEW_LOGOTYPE                     = "new_logotype";
    public static final String LIST_OF_CATEGORIES               = "categories";
    public static final String LIST_OF_PROVIDERS                = "providers";
    public static final String LIST_OF_PAYMENT_HISTORY_RECORDS  = "payments_records";
    public static final String LIST_OF_REGIONS                  = "regions";
    public static final String EDIT_REGIONS_PAGE                = "edit-regions";
    public static final String REGION_NAME                      = "region_name";
    public static final String REGION_ID                        = "region_id";
    public static final String REGION_ERROR                     = "region_error";
    public static final String EDIT_CATEGORIES_PAGE             = "edit-categories";
    public static final String CATEGORY_NAME                    = "category_name";
    public static final String CATEGORY_ID                      = "category_id";
    public static final String CATEGORY_ERROR                   = "category_error";
    public static final String TRUE                             = "true";
}
