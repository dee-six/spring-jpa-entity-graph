package com.six.mysample.springjpaentitygraph.subscription.domain;

import lombok.Getter;

@Getter
public enum LegalAgreementParameterValue {

    REPO_DETAILS("RepoDetails"), REPO_SMRA("Repo:SMRA"), MARGIN_CALL_SMRA("MarginCall:SMRA"),
    CCA_I_DETAILS("CCA I Details"), COLLATERAL_EXPOSURE_SNB_LIMIT_1("CollateralExposure:SNB Limit I"),
    COLLATERAL_EXPOSURE_SNB_PLEDGE("CollateralExposure:SNB Pledge"), MARGIN_CALL_SNB_LIMIT_1("MarginCall:SNB Limit I"),
    MARGIN_CALL_SNB_PLEDGE("MarginCall:SNB Pledge"), CCA_II_DETAILS("CCA II Details"), COLLATERAL_EXPOSURE_SNB_LIMIT_2(
            "CollateralExposure:SNB Limit II"), MARGIN_CALL_SNB_LIMIT_2("MarginCall:SNB Limit II"), NOT_CANCELED_NOT_MATURED("NOT_CANCELED_NOT_MATURED");

    private final String value;

    LegalAgreementParameterValue(String value) {
        this.value = value;
    }
}
