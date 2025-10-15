import { useState } from "react";
import { clsx } from "keycloakify/tools/clsx";
import type { I18n } from "../login/i18n";

interface RefreshButtonProps {
    className?: string;
    onClick?: () => void;
    i18n?: I18n;
}

export default function RefreshButton({ className, onClick, i18n }: RefreshButtonProps) {
    const [isRefreshing, setIsRefreshing] = useState(false);

    const handleRefresh = () => {
        setIsRefreshing(true);

        // Call custom onClick if provided
        if (onClick) {
            onClick();
        }

        // Reload the page after a short delay to show the loading state
        setTimeout(() => {
            window.location.reload();
        }, 200);
    };

    // Get translated text
    const refreshText = i18n?.msg("refreshButton") || "Actualiser";
    const refreshingText = i18n?.msg("refreshingButton") || "Actualisation...";

    return (
        <button
            type="button"
            className={clsx("kc-refresh-button", className)}
            onClick={handleRefresh}
            disabled={isRefreshing}
            title="Actualiser la page"
        >
            <svg
                width="16"
                height="16"
                viewBox="0 0 24 24"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
                className={clsx(
                    "kc-refresh-icon",
                    isRefreshing && "kc-refresh-icon-spinning"
                )}
            >
                <path
                    d="M1 4V10H7"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                />
                <path
                    d="M23 20V14H17"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                />
                <path
                    d="M20.49 9A9 9 0 0 0 5.64 5.64L1 10M23 14L18.36 18.36A9 9 0 0 1 3.51 15"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                />
            </svg>
            <span className="kc-refresh-text">
                {isRefreshing ? refreshingText : refreshText}
            </span>
        </button>
    );
}
