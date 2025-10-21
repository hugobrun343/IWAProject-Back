import { useEffect, useState } from "react";
import { clsx } from "keycloakify/tools/clsx";
import type { KcContext } from "../login/KcContext";

interface LanguageSwitcherProps {
    kcContext: KcContext;
}

export default function LanguageSwitcher({ kcContext }: LanguageSwitcherProps) {
    const [isLangMenuOpen, setIsLangMenuOpen] = useState(false);

    // Close language dropdown when clicking outside
    useEffect(() => {
        if (!isLangMenuOpen) return;

        const handleClickOutside = (event: MouseEvent) => {
            const target = event.target as HTMLElement;
            if (!target.closest(".kc-language-selector")) {
                setIsLangMenuOpen(false);
            }
        };

        document.addEventListener("click", handleClickOutside);
        return () => document.removeEventListener("click", handleClickOutside);
    }, [isLangMenuOpen]);

    const currentLocale = kcContext.locale?.currentLanguageTag ?? "en";
    const supportedLocales = kcContext.locale?.supported ?? [];

    // Don't render if internationalization is disabled or only one locale
    if (!kcContext.realm.internationalizationEnabled || supportedLocales.length <= 1) {
        return null;
    }

    return (
        <div className="kc-language-selector">
            <button
                type="button"
                className="kc-language-button"
                onClick={() => setIsLangMenuOpen(!isLangMenuOpen)}
                aria-label="Select language"
                aria-expanded={isLangMenuOpen}
            >
                <svg
                    width="20"
                    height="20"
                    viewBox="0 0 24 24"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                >
                    <path
                        d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z"
                        stroke="currentColor"
                        strokeWidth="2"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                    />
                    <path
                        d="M2 12H22"
                        stroke="currentColor"
                        strokeWidth="2"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                    />
                    <path
                        d="M12 2C14.5013 4.73835 15.9228 8.29203 16 12C15.9228 15.708 14.5013 19.2616 12 22C9.49872 19.2616 8.07725 15.708 8 12C8.07725 8.29203 9.49872 4.73835 12 2V2Z"
                        stroke="currentColor"
                        strokeWidth="2"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                    />
                </svg>
                <span>
                    {
                        supportedLocales.find(
                            locale => locale.languageTag === currentLocale
                        )?.label
                    }
                </span>
                <svg
                    width="16"
                    height="16"
                    viewBox="0 0 24 24"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                    className={clsx(
                        "kc-language-chevron",
                        isLangMenuOpen && "kc-language-chevron-open"
                    )}
                >
                    <path
                        d="M6 9L12 15L18 9"
                        stroke="currentColor"
                        strokeWidth="2"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                    />
                </svg>
            </button>
            {isLangMenuOpen && (
                <div className="kc-language-dropdown">
                    {supportedLocales.map(locale => (
                        <a
                            key={locale.languageTag}
                            href={locale.url}
                            className={clsx(
                                "kc-language-option",
                                locale.languageTag === currentLocale &&
                                    "kc-language-option-active"
                            )}
                            onClick={() => setIsLangMenuOpen(false)}
                        >
                            {locale.label}
                            {locale.languageTag === currentLocale && (
                                <svg
                                    width="16"
                                    height="16"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    xmlns="http://www.w3.org/2000/svg"
                                >
                                    <path
                                        d="M20 6L9 17L4 12"
                                        stroke="currentColor"
                                        strokeWidth="2"
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                    />
                                </svg>
                            )}
                        </a>
                    ))}
                </div>
            )}
        </div>
    );
}
