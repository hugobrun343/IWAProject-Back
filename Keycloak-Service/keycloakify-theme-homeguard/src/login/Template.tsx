import { useEffect } from "react";
import { clsx } from "keycloakify/tools/clsx";
import { kcSanitize } from "keycloakify/lib/kcSanitize";
import type { TemplateProps } from "keycloakify/login/TemplateProps";
import { useSetClassName } from "keycloakify/tools/useSetClassName";
import { useInitialize } from "keycloakify/login/Template.useInitialize";
import type { KcContext } from "./KcContext";
import type { I18n } from "./i18n";
import LanguageSwitcher from "../components/LanguageSwitcher";
import RefreshButton from "../components/RefreshButton";

export default function Template(props: TemplateProps<KcContext, I18n>) {
    const {
        displayInfo = false,
        displayMessage = true,
        headerNode,
        socialProvidersNode = null,
        infoNode = null,
        documentTitle,
        bodyClassName,
        kcContext,
        i18n,
        doUseDefaultCss,
        children
    } = props;

    const { msg } = i18n;

    const { isReadyToRender } = useInitialize({
        kcContext,
        doUseDefaultCss
    });

    useEffect(() => {
        if (!isReadyToRender) {
            return;
        }
        const title = documentTitle ?? msg("loginTitle", kcContext.realm.displayName);
        document.title = typeof title === "string" ? title : kcContext.realm.displayName;
    }, [isReadyToRender, documentTitle, msg, kcContext.realm.displayName]);

    useSetClassName({
        qualifiedName: "html",
        className: kcContext.realm.internationalizationEnabled ? clsx("layout-pf", "login-pf") : clsx("layout-pf", "login-pf", "kc-no-locale")
    });

    useSetClassName({
        qualifiedName: "body",
        className: bodyClassName ?? clsx(kcContext.pageId)
    });

    if (!isReadyToRender) {
        return null;
    }

    return (
        <div className="kc-split-layout">
            {/* Left Side - Form */}
            <div className="kc-form-side">
                <div className="kc-form-container">

                    {/* Header */}
                    {headerNode !== undefined ? (
                        <div className="kc-header">{headerNode}</div>
                    ) : (
                        <h1 className="kc-page-heading">{msg("loginTitle", kcContext.realm.displayName)}</h1>
                    )}

                    {/* Messages */}
                    {displayMessage && kcContext.message !== undefined && (
                        <div className={clsx("kc-alert", `kc-alert-${kcContext.message.type}`)}>
                            <span
                                dangerouslySetInnerHTML={{
                                    __html: kcSanitize(kcContext.message.summary)
                                }}
                            />
                        </div>
                    )}

                    {/* Refresh Button - Only on email verification page */}
                    {kcContext.pageId === "login-verify-email.ftl" && (
                        <div className="kc-refresh-section">
                            <RefreshButton i18n={i18n} />
                        </div>
                    )}

                    {/* Required Fields Notice - Hidden */}
                    {/* {displayRequiredFields && (
                        <div className="kc-required-notice">
                            <span className="kc-required-asterisk">*</span> {msg("requiredFields")}
                        </div>
                    )} */}

                    {/* Main Content */}
                    <div className="kc-form-content">{children}</div>

                    {/* Social Providers */}
                    {kcContext.social?.providers?.length > 0 && socialProvidersNode !== null && (
                        <>
                            <div className="kc-divider">
                                <span>OR WITH</span>
                            </div>
                            <div className="kc-social-section kc-social-buttons-only">{socialProvidersNode}</div>
                        </>
                    )}

                    {/* Info Section */}
                    {displayInfo && <div className="kc-info-section">{infoNode}</div>}

                    {/* Language Selector */}
                    <LanguageSwitcher kcContext={kcContext} />
                </div>
            </div>

            {/* Right Side - Benefits */}
            <div className="kc-benefits-side">
                <div className="kc-benefits-content">
                    <div className="kc-benefit-item">
                        <div className="kc-benefit-icon">
                            <svg width="32" height="32" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M19 3H5C3.89543 3 3 3.89543 3 5V19C3 20.1046 3.89543 21 5 21H19C20.1046 21 21 20.1046 21 19V5C21 3.89543 20.1046 3 19 3Z"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                                <path d="M7 7H7.01" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" />
                                <path d="M7 12H7.01" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" />
                                <path d="M7 17H7.01" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" />
                                <path d="M11 7H17" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
                                <path d="M11 12H17" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
                                <path d="M11 17H17" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
                            </svg>
                        </div>
                        <div className="kc-benefit-text">Capture Your Thoughts</div>
                    </div>
                    <div className="kc-benefit-item">
                        <div className="kc-benefit-icon">
                            <svg width="32" height="32" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M21.21 15.89C20.5738 17.3945 19.5788 18.7202 18.3119 19.7513C17.0449 20.7824 15.5447 21.4874 13.9424 21.8048C12.3401 22.1221 10.6844 22.0421 9.12012 21.5718C7.55585 21.1016 6.13005 20.2551 4.96902 19.1067C3.80799 17.9582 2.94971 16.5428 2.46476 14.9839C1.98981 13.425 1.8972 11.7706 2.20096 10.1646C2.50472 8.55857 3.19707 7.05063 4.21679 5.77203C5.23651 4.49342 6.55274 3.48332 8.05 2.83"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                                <path
                                    d="M22 12C22 10.6868 21.7413 9.38642 21.2388 8.17317C20.7362 6.95991 19.9997 5.85752 19.0711 4.92893C18.1425 4.00035 17.0401 3.26375 15.8268 2.7612C14.6136 2.25866 13.3132 2 12 2V12H22Z"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                            </svg>
                        </div>
                        <div className="kc-benefit-text">Sync Across Devices</div>
                    </div>
                    <div className="kc-benefit-item">
                        <div className="kc-benefit-icon">
                            <svg width="32" height="32" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <rect
                                    x="5"
                                    y="11"
                                    width="14"
                                    height="10"
                                    rx="2"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                                <path
                                    d="M12 17C12.5523 17 13 16.5523 13 16C13 15.4477 12.5523 15 12 15C11.4477 15 11 15.4477 11 16C11 16.5523 11.4477 17 12 17Z"
                                    fill="currentColor"
                                />
                                <path
                                    d="M17 11V7C17 5.67392 16.4732 4.40215 15.5355 3.46447C14.5979 2.52678 13.3261 2 12 2C10.6739 2 9.40215 2.52678 8.46447 3.46447C7.52678 4.40215 7 5.67392 7 7V11"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                            </svg>
                        </div>
                        <div className="kc-benefit-text">Secure & Private</div>
                    </div>
                    <div className="kc-benefit-item">
                        <div className="kc-benefit-icon">
                            <svg width="32" height="32" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M13 2L3 14H12L11 22L21 10H12L13 2Z"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                            </svg>
                        </div>
                        <div className="kc-benefit-text">Lightning Fast</div>
                    </div>
                    <div className="kc-benefit-item">
                        <div className="kc-benefit-icon">
                            <svg width="32" height="32" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M12 2C13.3132 2 14.6136 2.25866 15.8268 2.7612C17.0401 3.26375 18.1425 4.00035 19.0711 4.92893C19.9997 5.85752 20.7362 6.95991 21.2388 8.17317C21.7413 9.38642 22 10.6868 22 12C22 14.6522 20.9464 17.1957 19.0711 19.0711C17.1957 20.9464 14.6522 22 12 22C10.6868 22 9.38642 21.7413 8.17317 21.2388C6.95991 20.7362 5.85752 19.9997 4.92893 19.0711C3.00357 17.1957 2 14.6522 2 12C2 9.34784 3.05357 6.8043 4.92893 4.92893C6.8043 3.05357 9.34784 2 12 2Z"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                                <path d="M16 8L8 16" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
                                <path d="M8 8H16V16" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
                            </svg>
                        </div>
                        <div className="kc-benefit-text">Beautiful Design</div>
                    </div>
                </div>
            </div>
        </div>
    );
}
