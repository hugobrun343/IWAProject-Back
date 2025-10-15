/* eslint-disable @typescript-eslint/no-unused-vars */
import { i18nBuilder } from "keycloakify/login";
import type { ThemeName } from "../kc.gen";

/** @see: https://docs.keycloakify.dev/features/i18n */
const { useI18n, ofTypeI18n } = i18nBuilder
    .withThemeName<ThemeName>()
    .withCustomTranslations({
        en: {
            noAccount: "Don't have an account ?",
            doRegister: "Sign up",
            doLogIn: "Login",
            loginTitle: "HomeGuard Authentication",
            registerTitle: "Sign up for free",
            backToLogin: "Have an account ?",
            doSubmit: "Create account",
            refreshButton: "I have verified",
            refreshingButton: "Checking..."
        },
        fr: {
            noAccount: "Pas de compte ?",
            doRegister: "S'inscrire",
            doLogIn: "Se connecter",
            loginTitle: "Authentification HomeGuard",
            registerTitle: "Inscription gratuite",
            backToLogin: "Vous avez un compte ?",
            doSubmit: "Créer un compte",
            refreshButton: "J'ai vérifié",
            refreshingButton: "Vérification..."
        }
    })
    .build();

type I18n = typeof ofTypeI18n;

export { useI18n, type I18n };
