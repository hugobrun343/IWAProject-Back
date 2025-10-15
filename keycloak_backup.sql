--
-- PostgreSQL database dump
--

\restrict hOVFiwUhud45vbFCXIacVVSnrrbD7PdndZmohJqdSFeqca6f11pGNUeEPRNEPmJ

-- Dumped from database version 15.14
-- Dumped by pg_dump version 15.14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admin_event_entity; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.admin_event_entity (
    id character varying(36) NOT NULL,
    admin_event_time bigint,
    realm_id character varying(255),
    operation_type character varying(255),
    auth_realm_id character varying(255),
    auth_client_id character varying(255),
    auth_user_id character varying(255),
    ip_address character varying(255),
    resource_path character varying(2550),
    representation text,
    error character varying(255),
    resource_type character varying(64),
    details_json text
);


ALTER TABLE public.admin_event_entity OWNER TO keycloak_user;

--
-- Name: associated_policy; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.associated_policy (
    policy_id character varying(36) NOT NULL,
    associated_policy_id character varying(36) NOT NULL
);


ALTER TABLE public.associated_policy OWNER TO keycloak_user;

--
-- Name: authentication_execution; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.authentication_execution (
    id character varying(36) NOT NULL,
    alias character varying(255),
    authenticator character varying(36),
    realm_id character varying(36),
    flow_id character varying(36),
    requirement integer,
    priority integer,
    authenticator_flow boolean DEFAULT false NOT NULL,
    auth_flow_id character varying(36),
    auth_config character varying(36)
);


ALTER TABLE public.authentication_execution OWNER TO keycloak_user;

--
-- Name: authentication_flow; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.authentication_flow (
    id character varying(36) NOT NULL,
    alias character varying(255),
    description character varying(255),
    realm_id character varying(36),
    provider_id character varying(36) DEFAULT 'basic-flow'::character varying NOT NULL,
    top_level boolean DEFAULT false NOT NULL,
    built_in boolean DEFAULT false NOT NULL
);


ALTER TABLE public.authentication_flow OWNER TO keycloak_user;

--
-- Name: authenticator_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.authenticator_config (
    id character varying(36) NOT NULL,
    alias character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.authenticator_config OWNER TO keycloak_user;

--
-- Name: authenticator_config_entry; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.authenticator_config_entry (
    authenticator_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.authenticator_config_entry OWNER TO keycloak_user;

--
-- Name: broker_link; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.broker_link (
    identity_provider character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL,
    broker_user_id character varying(255),
    broker_username character varying(255),
    token text,
    user_id character varying(255) NOT NULL
);


ALTER TABLE public.broker_link OWNER TO keycloak_user;

--
-- Name: client; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.client (
    id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    full_scope_allowed boolean DEFAULT false NOT NULL,
    client_id character varying(255),
    not_before integer,
    public_client boolean DEFAULT false NOT NULL,
    secret character varying(255),
    base_url character varying(255),
    bearer_only boolean DEFAULT false NOT NULL,
    management_url character varying(255),
    surrogate_auth_required boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    protocol character varying(255),
    node_rereg_timeout integer DEFAULT 0,
    frontchannel_logout boolean DEFAULT false NOT NULL,
    consent_required boolean DEFAULT false NOT NULL,
    name character varying(255),
    service_accounts_enabled boolean DEFAULT false NOT NULL,
    client_authenticator_type character varying(255),
    root_url character varying(255),
    description character varying(255),
    registration_token character varying(255),
    standard_flow_enabled boolean DEFAULT true NOT NULL,
    implicit_flow_enabled boolean DEFAULT false NOT NULL,
    direct_access_grants_enabled boolean DEFAULT false NOT NULL,
    always_display_in_console boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client OWNER TO keycloak_user;

--
-- Name: client_attributes; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.client_attributes (
    client_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.client_attributes OWNER TO keycloak_user;

--
-- Name: client_auth_flow_bindings; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.client_auth_flow_bindings (
    client_id character varying(36) NOT NULL,
    flow_id character varying(36),
    binding_name character varying(255) NOT NULL
);


ALTER TABLE public.client_auth_flow_bindings OWNER TO keycloak_user;

--
-- Name: client_initial_access; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.client_initial_access (
    id character varying(36) NOT NULL,
    realm_id character varying(36) NOT NULL,
    "timestamp" integer,
    expiration integer,
    count integer,
    remaining_count integer
);


ALTER TABLE public.client_initial_access OWNER TO keycloak_user;

--
-- Name: client_node_registrations; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.client_node_registrations (
    client_id character varying(36) NOT NULL,
    value integer,
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_node_registrations OWNER TO keycloak_user;

--
-- Name: client_scope; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.client_scope (
    id character varying(36) NOT NULL,
    name character varying(255),
    realm_id character varying(36),
    description character varying(255),
    protocol character varying(255)
);


ALTER TABLE public.client_scope OWNER TO keycloak_user;

--
-- Name: client_scope_attributes; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.client_scope_attributes (
    scope_id character varying(36) NOT NULL,
    value character varying(2048),
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_scope_attributes OWNER TO keycloak_user;

--
-- Name: client_scope_client; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.client_scope_client (
    client_id character varying(255) NOT NULL,
    scope_id character varying(255) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client_scope_client OWNER TO keycloak_user;

--
-- Name: client_scope_role_mapping; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.client_scope_role_mapping (
    scope_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.client_scope_role_mapping OWNER TO keycloak_user;

--
-- Name: component; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.component (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_id character varying(36),
    provider_id character varying(36),
    provider_type character varying(255),
    realm_id character varying(36),
    sub_type character varying(255)
);


ALTER TABLE public.component OWNER TO keycloak_user;

--
-- Name: component_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.component_config (
    id character varying(36) NOT NULL,
    component_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.component_config OWNER TO keycloak_user;

--
-- Name: composite_role; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.composite_role (
    composite character varying(36) NOT NULL,
    child_role character varying(36) NOT NULL
);


ALTER TABLE public.composite_role OWNER TO keycloak_user;

--
-- Name: credential; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    user_id character varying(36),
    created_date bigint,
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer,
    version integer DEFAULT 0
);


ALTER TABLE public.credential OWNER TO keycloak_user;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO keycloak_user;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO keycloak_user;

--
-- Name: default_client_scope; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.default_client_scope (
    realm_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.default_client_scope OWNER TO keycloak_user;

--
-- Name: event_entity; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.event_entity (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    details_json character varying(2550),
    error character varying(255),
    ip_address character varying(255),
    realm_id character varying(255),
    session_id character varying(255),
    event_time bigint,
    type character varying(255),
    user_id character varying(255),
    details_json_long_value text
);


ALTER TABLE public.event_entity OWNER TO keycloak_user;

--
-- Name: fed_user_attribute; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.fed_user_attribute (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    value character varying(2024),
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.fed_user_attribute OWNER TO keycloak_user;

--
-- Name: fed_user_consent; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.fed_user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.fed_user_consent OWNER TO keycloak_user;

--
-- Name: fed_user_consent_cl_scope; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.fed_user_consent_cl_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.fed_user_consent_cl_scope OWNER TO keycloak_user;

--
-- Name: fed_user_credential; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.fed_user_credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    created_date bigint,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.fed_user_credential OWNER TO keycloak_user;

--
-- Name: fed_user_group_membership; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.fed_user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_group_membership OWNER TO keycloak_user;

--
-- Name: fed_user_required_action; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.fed_user_required_action (
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_required_action OWNER TO keycloak_user;

--
-- Name: fed_user_role_mapping; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.fed_user_role_mapping (
    role_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_role_mapping OWNER TO keycloak_user;

--
-- Name: federated_identity; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.federated_identity (
    identity_provider character varying(255) NOT NULL,
    realm_id character varying(36),
    federated_user_id character varying(255),
    federated_username character varying(255),
    token text,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_identity OWNER TO keycloak_user;

--
-- Name: federated_user; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.federated_user (
    id character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_user OWNER TO keycloak_user;

--
-- Name: group_attribute; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.group_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_attribute OWNER TO keycloak_user;

--
-- Name: group_role_mapping; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.group_role_mapping (
    role_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_role_mapping OWNER TO keycloak_user;

--
-- Name: identity_provider; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.identity_provider (
    internal_id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    provider_alias character varying(255),
    provider_id character varying(255),
    store_token boolean DEFAULT false NOT NULL,
    authenticate_by_default boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    add_token_role boolean DEFAULT true NOT NULL,
    trust_email boolean DEFAULT false NOT NULL,
    first_broker_login_flow_id character varying(36),
    post_broker_login_flow_id character varying(36),
    provider_display_name character varying(255),
    link_only boolean DEFAULT false NOT NULL,
    organization_id character varying(255),
    hide_on_login boolean DEFAULT false
);


ALTER TABLE public.identity_provider OWNER TO keycloak_user;

--
-- Name: identity_provider_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.identity_provider_config (
    identity_provider_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.identity_provider_config OWNER TO keycloak_user;

--
-- Name: identity_provider_mapper; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.identity_provider_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    idp_alias character varying(255) NOT NULL,
    idp_mapper_name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.identity_provider_mapper OWNER TO keycloak_user;

--
-- Name: idp_mapper_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.idp_mapper_config (
    idp_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.idp_mapper_config OWNER TO keycloak_user;

--
-- Name: jgroups_ping; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.jgroups_ping (
    address character varying(200) NOT NULL,
    name character varying(200),
    cluster_name character varying(200) NOT NULL,
    ip character varying(200) NOT NULL,
    coord boolean
);


ALTER TABLE public.jgroups_ping OWNER TO keycloak_user;

--
-- Name: keycloak_group; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.keycloak_group (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_group character varying(36) NOT NULL,
    realm_id character varying(36),
    type integer DEFAULT 0 NOT NULL,
    description character varying(255)
);


ALTER TABLE public.keycloak_group OWNER TO keycloak_user;

--
-- Name: keycloak_role; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.keycloak_role (
    id character varying(36) NOT NULL,
    client_realm_constraint character varying(255),
    client_role boolean DEFAULT false NOT NULL,
    description character varying(255),
    name character varying(255),
    realm_id character varying(255),
    client character varying(36),
    realm character varying(36)
);


ALTER TABLE public.keycloak_role OWNER TO keycloak_user;

--
-- Name: migration_model; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.migration_model (
    id character varying(36) NOT NULL,
    version character varying(36),
    update_time bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.migration_model OWNER TO keycloak_user;

--
-- Name: offline_client_session; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.offline_client_session (
    user_session_id character varying(36) NOT NULL,
    client_id character varying(255) NOT NULL,
    offline_flag character varying(4) NOT NULL,
    "timestamp" integer,
    data text,
    client_storage_provider character varying(36) DEFAULT 'local'::character varying NOT NULL,
    external_client_id character varying(255) DEFAULT 'local'::character varying NOT NULL,
    version integer DEFAULT 0
);


ALTER TABLE public.offline_client_session OWNER TO keycloak_user;

--
-- Name: offline_user_session; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.offline_user_session (
    user_session_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    created_on integer NOT NULL,
    offline_flag character varying(4) NOT NULL,
    data text,
    last_session_refresh integer DEFAULT 0 NOT NULL,
    broker_session_id character varying(1024),
    version integer DEFAULT 0
);


ALTER TABLE public.offline_user_session OWNER TO keycloak_user;

--
-- Name: org; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.org (
    id character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    realm_id character varying(255) NOT NULL,
    group_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(4000),
    alias character varying(255) NOT NULL,
    redirect_url character varying(2048)
);


ALTER TABLE public.org OWNER TO keycloak_user;

--
-- Name: org_domain; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.org_domain (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    verified boolean NOT NULL,
    org_id character varying(255) NOT NULL
);


ALTER TABLE public.org_domain OWNER TO keycloak_user;

--
-- Name: policy_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.policy_config (
    policy_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.policy_config OWNER TO keycloak_user;

--
-- Name: protocol_mapper; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.protocol_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    protocol character varying(255) NOT NULL,
    protocol_mapper_name character varying(255) NOT NULL,
    client_id character varying(36),
    client_scope_id character varying(36)
);


ALTER TABLE public.protocol_mapper OWNER TO keycloak_user;

--
-- Name: protocol_mapper_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.protocol_mapper_config (
    protocol_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.protocol_mapper_config OWNER TO keycloak_user;

--
-- Name: realm; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.realm (
    id character varying(36) NOT NULL,
    access_code_lifespan integer,
    user_action_lifespan integer,
    access_token_lifespan integer,
    account_theme character varying(255),
    admin_theme character varying(255),
    email_theme character varying(255),
    enabled boolean DEFAULT false NOT NULL,
    events_enabled boolean DEFAULT false NOT NULL,
    events_expiration bigint,
    login_theme character varying(255),
    name character varying(255),
    not_before integer,
    password_policy character varying(2550),
    registration_allowed boolean DEFAULT false NOT NULL,
    remember_me boolean DEFAULT false NOT NULL,
    reset_password_allowed boolean DEFAULT false NOT NULL,
    social boolean DEFAULT false NOT NULL,
    ssl_required character varying(255),
    sso_idle_timeout integer,
    sso_max_lifespan integer,
    update_profile_on_soc_login boolean DEFAULT false NOT NULL,
    verify_email boolean DEFAULT false NOT NULL,
    master_admin_client character varying(36),
    login_lifespan integer,
    internationalization_enabled boolean DEFAULT false NOT NULL,
    default_locale character varying(255),
    reg_email_as_username boolean DEFAULT false NOT NULL,
    admin_events_enabled boolean DEFAULT false NOT NULL,
    admin_events_details_enabled boolean DEFAULT false NOT NULL,
    edit_username_allowed boolean DEFAULT false NOT NULL,
    otp_policy_counter integer DEFAULT 0,
    otp_policy_window integer DEFAULT 1,
    otp_policy_period integer DEFAULT 30,
    otp_policy_digits integer DEFAULT 6,
    otp_policy_alg character varying(36) DEFAULT 'HmacSHA1'::character varying,
    otp_policy_type character varying(36) DEFAULT 'totp'::character varying,
    browser_flow character varying(36),
    registration_flow character varying(36),
    direct_grant_flow character varying(36),
    reset_credentials_flow character varying(36),
    client_auth_flow character varying(36),
    offline_session_idle_timeout integer DEFAULT 0,
    revoke_refresh_token boolean DEFAULT false NOT NULL,
    access_token_life_implicit integer DEFAULT 0,
    login_with_email_allowed boolean DEFAULT true NOT NULL,
    duplicate_emails_allowed boolean DEFAULT false NOT NULL,
    docker_auth_flow character varying(36),
    refresh_token_max_reuse integer DEFAULT 0,
    allow_user_managed_access boolean DEFAULT false NOT NULL,
    sso_max_lifespan_remember_me integer DEFAULT 0 NOT NULL,
    sso_idle_timeout_remember_me integer DEFAULT 0 NOT NULL,
    default_role character varying(255)
);


ALTER TABLE public.realm OWNER TO keycloak_user;

--
-- Name: realm_attribute; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.realm_attribute (
    name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    value text
);


ALTER TABLE public.realm_attribute OWNER TO keycloak_user;

--
-- Name: realm_default_groups; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.realm_default_groups (
    realm_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_default_groups OWNER TO keycloak_user;

--
-- Name: realm_enabled_event_types; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.realm_enabled_event_types (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_enabled_event_types OWNER TO keycloak_user;

--
-- Name: realm_events_listeners; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.realm_events_listeners (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_events_listeners OWNER TO keycloak_user;

--
-- Name: realm_localizations; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.realm_localizations (
    realm_id character varying(255) NOT NULL,
    locale character varying(255) NOT NULL,
    texts text NOT NULL
);


ALTER TABLE public.realm_localizations OWNER TO keycloak_user;

--
-- Name: realm_required_credential; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.realm_required_credential (
    type character varying(255) NOT NULL,
    form_label character varying(255),
    input boolean DEFAULT false NOT NULL,
    secret boolean DEFAULT false NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_required_credential OWNER TO keycloak_user;

--
-- Name: realm_smtp_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.realm_smtp_config (
    realm_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.realm_smtp_config OWNER TO keycloak_user;

--
-- Name: realm_supported_locales; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.realm_supported_locales (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_supported_locales OWNER TO keycloak_user;

--
-- Name: redirect_uris; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.redirect_uris (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.redirect_uris OWNER TO keycloak_user;

--
-- Name: required_action_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.required_action_config (
    required_action_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.required_action_config OWNER TO keycloak_user;

--
-- Name: required_action_provider; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.required_action_provider (
    id character varying(36) NOT NULL,
    alias character varying(255),
    name character varying(255),
    realm_id character varying(36),
    enabled boolean DEFAULT false NOT NULL,
    default_action boolean DEFAULT false NOT NULL,
    provider_id character varying(255),
    priority integer
);


ALTER TABLE public.required_action_provider OWNER TO keycloak_user;

--
-- Name: resource_attribute; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.resource_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    resource_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_attribute OWNER TO keycloak_user;

--
-- Name: resource_policy; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.resource_policy (
    resource_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_policy OWNER TO keycloak_user;

--
-- Name: resource_scope; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.resource_scope (
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_scope OWNER TO keycloak_user;

--
-- Name: resource_server; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.resource_server (
    id character varying(36) NOT NULL,
    allow_rs_remote_mgmt boolean DEFAULT false NOT NULL,
    policy_enforce_mode smallint NOT NULL,
    decision_strategy smallint DEFAULT 1 NOT NULL
);


ALTER TABLE public.resource_server OWNER TO keycloak_user;

--
-- Name: resource_server_perm_ticket; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.resource_server_perm_ticket (
    id character varying(36) NOT NULL,
    owner character varying(255) NOT NULL,
    requester character varying(255) NOT NULL,
    created_timestamp bigint NOT NULL,
    granted_timestamp bigint,
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36),
    resource_server_id character varying(36) NOT NULL,
    policy_id character varying(36)
);


ALTER TABLE public.resource_server_perm_ticket OWNER TO keycloak_user;

--
-- Name: resource_server_policy; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.resource_server_policy (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    type character varying(255) NOT NULL,
    decision_strategy smallint,
    logic smallint,
    resource_server_id character varying(36) NOT NULL,
    owner character varying(255)
);


ALTER TABLE public.resource_server_policy OWNER TO keycloak_user;

--
-- Name: resource_server_resource; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.resource_server_resource (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(255),
    icon_uri character varying(255),
    owner character varying(255) NOT NULL,
    resource_server_id character varying(36) NOT NULL,
    owner_managed_access boolean DEFAULT false NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_resource OWNER TO keycloak_user;

--
-- Name: resource_server_scope; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.resource_server_scope (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    icon_uri character varying(255),
    resource_server_id character varying(36) NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_scope OWNER TO keycloak_user;

--
-- Name: resource_uris; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.resource_uris (
    resource_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.resource_uris OWNER TO keycloak_user;

--
-- Name: revoked_token; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.revoked_token (
    id character varying(255) NOT NULL,
    expire bigint NOT NULL
);


ALTER TABLE public.revoked_token OWNER TO keycloak_user;

--
-- Name: role_attribute; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.role_attribute (
    id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255)
);


ALTER TABLE public.role_attribute OWNER TO keycloak_user;

--
-- Name: scope_mapping; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.scope_mapping (
    client_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_mapping OWNER TO keycloak_user;

--
-- Name: scope_policy; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.scope_policy (
    scope_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_policy OWNER TO keycloak_user;

--
-- Name: server_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.server_config (
    server_config_key character varying(255) NOT NULL,
    value text NOT NULL,
    version integer DEFAULT 0
);


ALTER TABLE public.server_config OWNER TO keycloak_user;

--
-- Name: user_attribute; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_attribute (
    name character varying(255) NOT NULL,
    value character varying(255),
    user_id character varying(36) NOT NULL,
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.user_attribute OWNER TO keycloak_user;

--
-- Name: user_consent; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(36) NOT NULL,
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.user_consent OWNER TO keycloak_user;

--
-- Name: user_consent_client_scope; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_consent_client_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.user_consent_client_scope OWNER TO keycloak_user;

--
-- Name: user_entity; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_entity (
    id character varying(36) NOT NULL,
    email character varying(255),
    email_constraint character varying(255),
    email_verified boolean DEFAULT false NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    federation_link character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    realm_id character varying(255),
    username character varying(255),
    created_timestamp bigint,
    service_account_client_link character varying(255),
    not_before integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.user_entity OWNER TO keycloak_user;

--
-- Name: user_federation_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_federation_config (
    user_federation_provider_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_config OWNER TO keycloak_user;

--
-- Name: user_federation_mapper; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_federation_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    federation_provider_id character varying(36) NOT NULL,
    federation_mapper_type character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.user_federation_mapper OWNER TO keycloak_user;

--
-- Name: user_federation_mapper_config; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_federation_mapper_config (
    user_federation_mapper_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_mapper_config OWNER TO keycloak_user;

--
-- Name: user_federation_provider; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_federation_provider (
    id character varying(36) NOT NULL,
    changed_sync_period integer,
    display_name character varying(255),
    full_sync_period integer,
    last_sync integer,
    priority integer,
    provider_name character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.user_federation_provider OWNER TO keycloak_user;

--
-- Name: user_group_membership; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(36) NOT NULL,
    membership_type character varying(255) NOT NULL
);


ALTER TABLE public.user_group_membership OWNER TO keycloak_user;

--
-- Name: user_required_action; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_required_action (
    user_id character varying(36) NOT NULL,
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL
);


ALTER TABLE public.user_required_action OWNER TO keycloak_user;

--
-- Name: user_role_mapping; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.user_role_mapping (
    role_id character varying(255) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.user_role_mapping OWNER TO keycloak_user;

--
-- Name: web_origins; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.web_origins (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.web_origins OWNER TO keycloak_user;

--
-- Name: workflow_state; Type: TABLE; Schema: public; Owner: keycloak_user
--

CREATE TABLE public.workflow_state (
    execution_id character varying(255) NOT NULL,
    resource_id character varying(255) NOT NULL,
    workflow_id character varying(255) NOT NULL,
    workflow_provider_id character varying(255),
    resource_type character varying(255),
    scheduled_step_id character varying(255),
    scheduled_step_timestamp bigint
);


ALTER TABLE public.workflow_state OWNER TO keycloak_user;

--
-- Data for Name: admin_event_entity; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.admin_event_entity (id, admin_event_time, realm_id, operation_type, auth_realm_id, auth_client_id, auth_user_id, ip_address, resource_path, representation, error, resource_type, details_json) FROM stdin;
\.


--
-- Data for Name: associated_policy; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.associated_policy (policy_id, associated_policy_id) FROM stdin;
\.


--
-- Data for Name: authentication_execution; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.authentication_execution (id, alias, authenticator, realm_id, flow_id, requirement, priority, authenticator_flow, auth_flow_id, auth_config) FROM stdin;
bc07d032-c4ef-4c5b-b6b8-d78e57db8749	\N	auth-cookie	2522a2b1-50e1-43f3-947e-15bab8e65d6a	28d70fa4-2075-4806-a4bc-a1aef12f65f4	2	10	f	\N	\N
0647baef-a897-4523-b925-86c86661d503	\N	auth-spnego	2522a2b1-50e1-43f3-947e-15bab8e65d6a	28d70fa4-2075-4806-a4bc-a1aef12f65f4	3	20	f	\N	\N
4eda4bba-2738-469e-9659-c580d1e917b1	\N	identity-provider-redirector	2522a2b1-50e1-43f3-947e-15bab8e65d6a	28d70fa4-2075-4806-a4bc-a1aef12f65f4	2	25	f	\N	\N
e5af1958-1721-418a-9e8f-937d5f15282a	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	28d70fa4-2075-4806-a4bc-a1aef12f65f4	2	30	t	5fe8dc92-2c57-4f98-a506-b7f34d0933f7	\N
d84a64ab-9e8a-436b-a1cf-d73aca7a95d4	\N	auth-username-password-form	2522a2b1-50e1-43f3-947e-15bab8e65d6a	5fe8dc92-2c57-4f98-a506-b7f34d0933f7	0	10	f	\N	\N
d6c133e6-39e0-47b9-984a-21209a85a383	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	5fe8dc92-2c57-4f98-a506-b7f34d0933f7	1	20	t	c5456830-bb7e-4401-aca3-9ece536f3815	\N
d6eef432-393e-48bd-9071-0cba738dec11	\N	conditional-user-configured	2522a2b1-50e1-43f3-947e-15bab8e65d6a	c5456830-bb7e-4401-aca3-9ece536f3815	0	10	f	\N	\N
3011126c-60ca-4e3f-a4df-5387e5e00925	\N	conditional-credential	2522a2b1-50e1-43f3-947e-15bab8e65d6a	c5456830-bb7e-4401-aca3-9ece536f3815	0	20	f	\N	b94db35f-abfc-4e19-8d8b-18435b6985fe
363f67c4-ade2-4baa-a014-3cd7b63f8888	\N	auth-otp-form	2522a2b1-50e1-43f3-947e-15bab8e65d6a	c5456830-bb7e-4401-aca3-9ece536f3815	2	30	f	\N	\N
d2cd46b5-df02-4de0-a048-95aae9c04e3d	\N	webauthn-authenticator	2522a2b1-50e1-43f3-947e-15bab8e65d6a	c5456830-bb7e-4401-aca3-9ece536f3815	3	40	f	\N	\N
3d631dc4-455a-4dc9-a3eb-0e60c57b40f0	\N	auth-recovery-authn-code-form	2522a2b1-50e1-43f3-947e-15bab8e65d6a	c5456830-bb7e-4401-aca3-9ece536f3815	3	50	f	\N	\N
b2f81d02-a795-4ca7-896e-1176fc2396b4	\N	direct-grant-validate-username	2522a2b1-50e1-43f3-947e-15bab8e65d6a	5fc6a273-731f-48d8-a596-f3c967462384	0	10	f	\N	\N
82b44a2e-2b2d-435b-8b25-3b3db333b9ab	\N	direct-grant-validate-password	2522a2b1-50e1-43f3-947e-15bab8e65d6a	5fc6a273-731f-48d8-a596-f3c967462384	0	20	f	\N	\N
4dbeaeb2-407c-4f6c-8865-fb1993e14fad	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	5fc6a273-731f-48d8-a596-f3c967462384	1	30	t	1180ff5c-524c-4d05-a585-325ccf6d2276	\N
ef726f77-70ae-4444-8fe9-6e4ee619c343	\N	conditional-user-configured	2522a2b1-50e1-43f3-947e-15bab8e65d6a	1180ff5c-524c-4d05-a585-325ccf6d2276	0	10	f	\N	\N
2169353b-4a20-4ae8-abcc-f0caf764a935	\N	direct-grant-validate-otp	2522a2b1-50e1-43f3-947e-15bab8e65d6a	1180ff5c-524c-4d05-a585-325ccf6d2276	0	20	f	\N	\N
ca358a9e-ca32-47b8-a8d9-d6423dc42f32	\N	registration-page-form	2522a2b1-50e1-43f3-947e-15bab8e65d6a	522536ed-6210-4135-8d84-97e88d0fd2cb	0	10	t	e2063d74-d4fe-4c75-9db7-5623496d580d	\N
85291456-902d-4bb4-b79d-a32f13cdffef	\N	registration-user-creation	2522a2b1-50e1-43f3-947e-15bab8e65d6a	e2063d74-d4fe-4c75-9db7-5623496d580d	0	20	f	\N	\N
77561eab-e919-4a62-8e62-7bb8283f238e	\N	registration-password-action	2522a2b1-50e1-43f3-947e-15bab8e65d6a	e2063d74-d4fe-4c75-9db7-5623496d580d	0	50	f	\N	\N
1b7f5f5c-4dfa-4948-9dfc-d4b265c5afc7	\N	registration-recaptcha-action	2522a2b1-50e1-43f3-947e-15bab8e65d6a	e2063d74-d4fe-4c75-9db7-5623496d580d	3	60	f	\N	\N
ee7bafd2-2608-4bca-9838-4dd418bf3262	\N	registration-terms-and-conditions	2522a2b1-50e1-43f3-947e-15bab8e65d6a	e2063d74-d4fe-4c75-9db7-5623496d580d	3	70	f	\N	\N
a08a0aa4-3fca-4918-8a57-9a61bed0a6e0	\N	reset-credentials-choose-user	2522a2b1-50e1-43f3-947e-15bab8e65d6a	4c78fa22-7ad5-41e5-b4ee-00b3cae0c6ff	0	10	f	\N	\N
f41fbd38-67e5-4af9-bd05-7eb10ceecd94	\N	reset-credential-email	2522a2b1-50e1-43f3-947e-15bab8e65d6a	4c78fa22-7ad5-41e5-b4ee-00b3cae0c6ff	0	20	f	\N	\N
ff1c8156-b477-48b7-b5d2-5911d57db0d9	\N	reset-password	2522a2b1-50e1-43f3-947e-15bab8e65d6a	4c78fa22-7ad5-41e5-b4ee-00b3cae0c6ff	0	30	f	\N	\N
4fe5ae75-5a7c-4903-99fe-b514e36daa10	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	4c78fa22-7ad5-41e5-b4ee-00b3cae0c6ff	1	40	t	66edcfb7-b6a7-4cf4-838c-2ae35268a8bd	\N
12336357-8d61-4957-b36f-d7decd8c34b7	\N	conditional-user-configured	2522a2b1-50e1-43f3-947e-15bab8e65d6a	66edcfb7-b6a7-4cf4-838c-2ae35268a8bd	0	10	f	\N	\N
1d1618ea-6434-4207-a812-127f7d72773c	\N	reset-otp	2522a2b1-50e1-43f3-947e-15bab8e65d6a	66edcfb7-b6a7-4cf4-838c-2ae35268a8bd	0	20	f	\N	\N
188f5305-1525-4c3c-b463-df1a5a980ed5	\N	client-secret	2522a2b1-50e1-43f3-947e-15bab8e65d6a	254fbf8c-fdf8-405d-9c48-051945d135c5	2	10	f	\N	\N
aea9aa55-87dd-4d52-ad25-918ae1902826	\N	client-jwt	2522a2b1-50e1-43f3-947e-15bab8e65d6a	254fbf8c-fdf8-405d-9c48-051945d135c5	2	20	f	\N	\N
8b7fdfca-144d-49b8-a6e3-f9306576a87c	\N	client-secret-jwt	2522a2b1-50e1-43f3-947e-15bab8e65d6a	254fbf8c-fdf8-405d-9c48-051945d135c5	2	30	f	\N	\N
380a78d3-cb7a-4bcd-b8dc-fb76b2902dea	\N	client-x509	2522a2b1-50e1-43f3-947e-15bab8e65d6a	254fbf8c-fdf8-405d-9c48-051945d135c5	2	40	f	\N	\N
b16bf318-4614-415d-8735-d26fa21897eb	\N	idp-review-profile	2522a2b1-50e1-43f3-947e-15bab8e65d6a	cc584f3f-d6e7-4aeb-a371-a931115eec16	0	10	f	\N	30af1446-cae2-4d07-9c46-fc54028b3ef3
4d6bf9d0-7883-4b8c-815b-0beee4c928f7	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	cc584f3f-d6e7-4aeb-a371-a931115eec16	0	20	t	c6698c03-45c0-4ab3-b84c-0b3bcfcce8b9	\N
d5072489-b222-4a3b-a15e-446ef52b1383	\N	idp-create-user-if-unique	2522a2b1-50e1-43f3-947e-15bab8e65d6a	c6698c03-45c0-4ab3-b84c-0b3bcfcce8b9	2	10	f	\N	50ce939f-0a8d-4e98-ac0c-295274df3f9f
091f80b3-4351-4d7e-a5f8-1ba4289a7173	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	c6698c03-45c0-4ab3-b84c-0b3bcfcce8b9	2	20	t	1bb391a2-6188-47cf-aba5-7c9c1f1b2dac	\N
8820d659-320b-4dc4-9709-85d833b651a7	\N	idp-confirm-link	2522a2b1-50e1-43f3-947e-15bab8e65d6a	1bb391a2-6188-47cf-aba5-7c9c1f1b2dac	0	10	f	\N	\N
6b60bd26-40ff-4c33-88c3-0f8c28e9f5c8	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	1bb391a2-6188-47cf-aba5-7c9c1f1b2dac	0	20	t	56ca3be3-4816-4324-a9ae-f94b28be2e7a	\N
fd0cdad6-976f-473f-9d94-23f9eb2087d2	\N	idp-email-verification	2522a2b1-50e1-43f3-947e-15bab8e65d6a	56ca3be3-4816-4324-a9ae-f94b28be2e7a	2	10	f	\N	\N
357ec990-c5aa-4dcb-b2af-43d1d38605bf	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	56ca3be3-4816-4324-a9ae-f94b28be2e7a	2	20	t	ae3079b9-3ce8-4faf-a53b-177f1f7419e9	\N
c56a5222-e258-4bb0-a4bb-4ac7cf02549d	\N	idp-username-password-form	2522a2b1-50e1-43f3-947e-15bab8e65d6a	ae3079b9-3ce8-4faf-a53b-177f1f7419e9	0	10	f	\N	\N
55822dde-2308-4f3e-bc2f-1d5a2a18c709	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	ae3079b9-3ce8-4faf-a53b-177f1f7419e9	1	20	t	9a2ebe94-2a07-450d-a7af-51cd338cd2cf	\N
7ab316ed-d7e5-4d0d-b000-ea54d8f546aa	\N	conditional-user-configured	2522a2b1-50e1-43f3-947e-15bab8e65d6a	9a2ebe94-2a07-450d-a7af-51cd338cd2cf	0	10	f	\N	\N
7ccaff3c-80e3-4596-8a4d-126f7c99cc7f	\N	conditional-credential	2522a2b1-50e1-43f3-947e-15bab8e65d6a	9a2ebe94-2a07-450d-a7af-51cd338cd2cf	0	20	f	\N	84a50476-c71c-4f47-aa5f-80873fa5b9f9
f35f328f-3605-42ae-839c-a1ff5ff96ec8	\N	auth-otp-form	2522a2b1-50e1-43f3-947e-15bab8e65d6a	9a2ebe94-2a07-450d-a7af-51cd338cd2cf	2	30	f	\N	\N
a2881802-12ae-4904-9234-82b06dc267db	\N	webauthn-authenticator	2522a2b1-50e1-43f3-947e-15bab8e65d6a	9a2ebe94-2a07-450d-a7af-51cd338cd2cf	3	40	f	\N	\N
5895dacc-87f1-4099-bbd5-eaae937aeaed	\N	auth-recovery-authn-code-form	2522a2b1-50e1-43f3-947e-15bab8e65d6a	9a2ebe94-2a07-450d-a7af-51cd338cd2cf	3	50	f	\N	\N
46ca2416-fdf8-4451-95a1-818b9afc4b02	\N	http-basic-authenticator	2522a2b1-50e1-43f3-947e-15bab8e65d6a	5eca218f-3693-47d7-a7b1-5236fde04ac8	0	10	f	\N	\N
5deba2c7-1b25-4185-a341-a3a1d7b0a3d1	\N	docker-http-basic-authenticator	2522a2b1-50e1-43f3-947e-15bab8e65d6a	08b3cedb-84fe-4dfb-8f95-54973c56d5e2	0	10	f	\N	\N
\.


--
-- Data for Name: authentication_flow; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.authentication_flow (id, alias, description, realm_id, provider_id, top_level, built_in) FROM stdin;
28d70fa4-2075-4806-a4bc-a1aef12f65f4	browser	Browser based authentication	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	t	t
5fe8dc92-2c57-4f98-a506-b7f34d0933f7	forms	Username, password, otp and other auth forms.	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	f	t
c5456830-bb7e-4401-aca3-9ece536f3815	Browser - Conditional 2FA	Flow to determine if any 2FA is required for the authentication	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	f	t
5fc6a273-731f-48d8-a596-f3c967462384	direct grant	OpenID Connect Resource Owner Grant	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	t	t
1180ff5c-524c-4d05-a585-325ccf6d2276	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	f	t
522536ed-6210-4135-8d84-97e88d0fd2cb	registration	Registration flow	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	t	t
e2063d74-d4fe-4c75-9db7-5623496d580d	registration form	Registration form	2522a2b1-50e1-43f3-947e-15bab8e65d6a	form-flow	f	t
4c78fa22-7ad5-41e5-b4ee-00b3cae0c6ff	reset credentials	Reset credentials for a user if they forgot their password or something	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	t	t
66edcfb7-b6a7-4cf4-838c-2ae35268a8bd	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	f	t
254fbf8c-fdf8-405d-9c48-051945d135c5	clients	Base authentication for clients	2522a2b1-50e1-43f3-947e-15bab8e65d6a	client-flow	t	t
cc584f3f-d6e7-4aeb-a371-a931115eec16	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	t	t
c6698c03-45c0-4ab3-b84c-0b3bcfcce8b9	User creation or linking	Flow for the existing/non-existing user alternatives	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	f	t
1bb391a2-6188-47cf-aba5-7c9c1f1b2dac	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	f	t
56ca3be3-4816-4324-a9ae-f94b28be2e7a	Account verification options	Method with which to verity the existing account	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	f	t
ae3079b9-3ce8-4faf-a53b-177f1f7419e9	Verify Existing Account by Re-authentication	Reauthentication of existing account	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	f	t
9a2ebe94-2a07-450d-a7af-51cd338cd2cf	First broker login - Conditional 2FA	Flow to determine if any 2FA is required for the authentication	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	f	t
5eca218f-3693-47d7-a7b1-5236fde04ac8	saml ecp	SAML ECP Profile Authentication Flow	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	t	t
08b3cedb-84fe-4dfb-8f95-54973c56d5e2	docker auth	Used by Docker clients to authenticate against the IDP	2522a2b1-50e1-43f3-947e-15bab8e65d6a	basic-flow	t	t
\.


--
-- Data for Name: authenticator_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.authenticator_config (id, alias, realm_id) FROM stdin;
b94db35f-abfc-4e19-8d8b-18435b6985fe	browser-conditional-credential	2522a2b1-50e1-43f3-947e-15bab8e65d6a
30af1446-cae2-4d07-9c46-fc54028b3ef3	review profile config	2522a2b1-50e1-43f3-947e-15bab8e65d6a
50ce939f-0a8d-4e98-ac0c-295274df3f9f	create unique user config	2522a2b1-50e1-43f3-947e-15bab8e65d6a
84a50476-c71c-4f47-aa5f-80873fa5b9f9	first-broker-login-conditional-credential	2522a2b1-50e1-43f3-947e-15bab8e65d6a
\.


--
-- Data for Name: authenticator_config_entry; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.authenticator_config_entry (authenticator_id, value, name) FROM stdin;
30af1446-cae2-4d07-9c46-fc54028b3ef3	missing	update.profile.on.first.login
50ce939f-0a8d-4e98-ac0c-295274df3f9f	false	require.password.update.after.registration
84a50476-c71c-4f47-aa5f-80873fa5b9f9	webauthn-passwordless	credentials
b94db35f-abfc-4e19-8d8b-18435b6985fe	webauthn-passwordless	credentials
\.


--
-- Data for Name: broker_link; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.broker_link (identity_provider, storage_provider_id, realm_id, broker_user_id, broker_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.client (id, enabled, full_scope_allowed, client_id, not_before, public_client, secret, base_url, bearer_only, management_url, surrogate_auth_required, realm_id, protocol, node_rereg_timeout, frontchannel_logout, consent_required, name, service_accounts_enabled, client_authenticator_type, root_url, description, registration_token, standard_flow_enabled, implicit_flow_enabled, direct_access_grants_enabled, always_display_in_console) FROM stdin;
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	f	master-realm	0	f	\N	\N	t	\N	f	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N	0	f	f	master Realm	f	client-secret	\N	\N	\N	t	f	f	f
822c0def-a413-405f-ada7-901a19f0e14a	t	f	account	0	t	\N	/realms/master/account/	f	\N	f	2522a2b1-50e1-43f3-947e-15bab8e65d6a	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
d3fd2493-8a37-4cf7-9c70-b05841741543	t	f	account-console	0	t	\N	/realms/master/account/	f	\N	f	2522a2b1-50e1-43f3-947e-15bab8e65d6a	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
ce701a82-9534-4f28-8501-8af5485fdbef	t	f	broker	0	f	\N	\N	t	\N	f	2522a2b1-50e1-43f3-947e-15bab8e65d6a	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
d1318f74-13c0-4a48-a30a-08b679c21f15	t	t	security-admin-console	0	t	\N	/admin/master/console/	f	\N	f	2522a2b1-50e1-43f3-947e-15bab8e65d6a	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
99c140d7-b340-4425-9fde-800f2f552715	t	t	admin-cli	0	f	6JgsWs7GZQE4zca8Guke3ASu99dFrNWJ		f		f	2522a2b1-50e1-43f3-947e-15bab8e65d6a	openid-connect	0	f	f	${client_admin-cli}	t	client-secret			\N	f	f	t	f
7866504f-df5b-40ad-8128-3ac0e63fc85a	t	t	iwa-client	0	f	weYCghxJ6Xaj1lbIvxHI4hNHM7YKDUgo		f		f	2522a2b1-50e1-43f3-947e-15bab8e65d6a	openid-connect	-1	t	f		t	client-secret			\N	t	f	t	f
\.


--
-- Data for Name: client_attributes; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.client_attributes (client_id, name, value) FROM stdin;
822c0def-a413-405f-ada7-901a19f0e14a	post.logout.redirect.uris	+
d3fd2493-8a37-4cf7-9c70-b05841741543	post.logout.redirect.uris	+
d3fd2493-8a37-4cf7-9c70-b05841741543	pkce.code.challenge.method	S256
d1318f74-13c0-4a48-a30a-08b679c21f15	post.logout.redirect.uris	+
d1318f74-13c0-4a48-a30a-08b679c21f15	pkce.code.challenge.method	S256
d1318f74-13c0-4a48-a30a-08b679c21f15	client.use.lightweight.access.token.enabled	true
99c140d7-b340-4425-9fde-800f2f552715	client.use.lightweight.access.token.enabled	true
99c140d7-b340-4425-9fde-800f2f552715	client.secret.creation.time	1760528870
99c140d7-b340-4425-9fde-800f2f552715	realm_client	false
99c140d7-b340-4425-9fde-800f2f552715	standard.token.exchange.enabled	false
99c140d7-b340-4425-9fde-800f2f552715	oauth2.device.authorization.grant.enabled	false
99c140d7-b340-4425-9fde-800f2f552715	oidc.ciba.grant.enabled	false
99c140d7-b340-4425-9fde-800f2f552715	dpop.bound.access.tokens	false
99c140d7-b340-4425-9fde-800f2f552715	display.on.consent.screen	false
99c140d7-b340-4425-9fde-800f2f552715	backchannel.logout.session.required	true
99c140d7-b340-4425-9fde-800f2f552715	backchannel.logout.revoke.offline.tokens	false
7866504f-df5b-40ad-8128-3ac0e63fc85a	client.secret.creation.time	1760533841
7866504f-df5b-40ad-8128-3ac0e63fc85a	standard.token.exchange.enabled	false
7866504f-df5b-40ad-8128-3ac0e63fc85a	oauth2.device.authorization.grant.enabled	false
7866504f-df5b-40ad-8128-3ac0e63fc85a	oidc.ciba.grant.enabled	false
7866504f-df5b-40ad-8128-3ac0e63fc85a	pkce.code.challenge.method	S256
7866504f-df5b-40ad-8128-3ac0e63fc85a	dpop.bound.access.tokens	false
7866504f-df5b-40ad-8128-3ac0e63fc85a	backchannel.logout.session.required	true
7866504f-df5b-40ad-8128-3ac0e63fc85a	backchannel.logout.revoke.offline.tokens	false
\.


--
-- Data for Name: client_auth_flow_bindings; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.client_auth_flow_bindings (client_id, flow_id, binding_name) FROM stdin;
\.


--
-- Data for Name: client_initial_access; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.client_initial_access (id, realm_id, "timestamp", expiration, count, remaining_count) FROM stdin;
\.


--
-- Data for Name: client_node_registrations; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.client_node_registrations (client_id, value, name) FROM stdin;
\.


--
-- Data for Name: client_scope; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.client_scope (id, name, realm_id, description, protocol) FROM stdin;
ad44ff53-1040-4b35-9e8f-ede27da2a5cb	offline_access	2522a2b1-50e1-43f3-947e-15bab8e65d6a	OpenID Connect built-in scope: offline_access	openid-connect
7f4254c5-f8c3-4aed-90d8-a624c4af8b52	role_list	2522a2b1-50e1-43f3-947e-15bab8e65d6a	SAML role list	saml
52200320-e220-422d-b453-f98a8e250420	saml_organization	2522a2b1-50e1-43f3-947e-15bab8e65d6a	Organization Membership	saml
d0485413-da03-4c3a-91a5-4219e7551095	profile	2522a2b1-50e1-43f3-947e-15bab8e65d6a	OpenID Connect built-in scope: profile	openid-connect
73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	email	2522a2b1-50e1-43f3-947e-15bab8e65d6a	OpenID Connect built-in scope: email	openid-connect
d5b5a828-823a-42ee-a4e3-c3532c896426	address	2522a2b1-50e1-43f3-947e-15bab8e65d6a	OpenID Connect built-in scope: address	openid-connect
f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	phone	2522a2b1-50e1-43f3-947e-15bab8e65d6a	OpenID Connect built-in scope: phone	openid-connect
5cc32155-3949-4283-980d-bc97497c21e8	roles	2522a2b1-50e1-43f3-947e-15bab8e65d6a	OpenID Connect scope for add user roles to the access token	openid-connect
92bca07a-04d6-4cee-970b-9a36b484c33c	web-origins	2522a2b1-50e1-43f3-947e-15bab8e65d6a	OpenID Connect scope for add allowed web origins to the access token	openid-connect
1ee89a9b-aa2c-4a44-896c-a942160b8976	microprofile-jwt	2522a2b1-50e1-43f3-947e-15bab8e65d6a	Microprofile - JWT built-in scope	openid-connect
bd40343b-10d5-4eaf-8fd0-6bc5122ff280	acr	2522a2b1-50e1-43f3-947e-15bab8e65d6a	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
663d10f9-9ab7-463a-aade-b824010d8b28	basic	2522a2b1-50e1-43f3-947e-15bab8e65d6a	OpenID Connect scope for add all basic claims to the token	openid-connect
2dfed20c-b325-4d0c-a0db-834457fb7bd5	service_account	2522a2b1-50e1-43f3-947e-15bab8e65d6a	Specific scope for a client enabled for service accounts	openid-connect
bef193f9-e920-42e7-a3c9-221caa197ad2	organization	2522a2b1-50e1-43f3-947e-15bab8e65d6a	Additional claims about the organization a subject belongs to	openid-connect
\.


--
-- Data for Name: client_scope_attributes; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.client_scope_attributes (scope_id, value, name) FROM stdin;
ad44ff53-1040-4b35-9e8f-ede27da2a5cb	true	display.on.consent.screen
ad44ff53-1040-4b35-9e8f-ede27da2a5cb	${offlineAccessScopeConsentText}	consent.screen.text
7f4254c5-f8c3-4aed-90d8-a624c4af8b52	true	display.on.consent.screen
7f4254c5-f8c3-4aed-90d8-a624c4af8b52	${samlRoleListScopeConsentText}	consent.screen.text
52200320-e220-422d-b453-f98a8e250420	false	display.on.consent.screen
d0485413-da03-4c3a-91a5-4219e7551095	true	display.on.consent.screen
d0485413-da03-4c3a-91a5-4219e7551095	${profileScopeConsentText}	consent.screen.text
d0485413-da03-4c3a-91a5-4219e7551095	true	include.in.token.scope
73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	true	display.on.consent.screen
73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	${emailScopeConsentText}	consent.screen.text
73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	true	include.in.token.scope
d5b5a828-823a-42ee-a4e3-c3532c896426	true	display.on.consent.screen
d5b5a828-823a-42ee-a4e3-c3532c896426	${addressScopeConsentText}	consent.screen.text
d5b5a828-823a-42ee-a4e3-c3532c896426	true	include.in.token.scope
f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	true	display.on.consent.screen
f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	${phoneScopeConsentText}	consent.screen.text
f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	true	include.in.token.scope
5cc32155-3949-4283-980d-bc97497c21e8	true	display.on.consent.screen
5cc32155-3949-4283-980d-bc97497c21e8	${rolesScopeConsentText}	consent.screen.text
5cc32155-3949-4283-980d-bc97497c21e8	false	include.in.token.scope
92bca07a-04d6-4cee-970b-9a36b484c33c	false	display.on.consent.screen
92bca07a-04d6-4cee-970b-9a36b484c33c		consent.screen.text
92bca07a-04d6-4cee-970b-9a36b484c33c	false	include.in.token.scope
1ee89a9b-aa2c-4a44-896c-a942160b8976	false	display.on.consent.screen
1ee89a9b-aa2c-4a44-896c-a942160b8976	true	include.in.token.scope
bd40343b-10d5-4eaf-8fd0-6bc5122ff280	false	display.on.consent.screen
bd40343b-10d5-4eaf-8fd0-6bc5122ff280	false	include.in.token.scope
663d10f9-9ab7-463a-aade-b824010d8b28	false	display.on.consent.screen
663d10f9-9ab7-463a-aade-b824010d8b28	false	include.in.token.scope
2dfed20c-b325-4d0c-a0db-834457fb7bd5	false	display.on.consent.screen
2dfed20c-b325-4d0c-a0db-834457fb7bd5	false	include.in.token.scope
bef193f9-e920-42e7-a3c9-221caa197ad2	true	display.on.consent.screen
bef193f9-e920-42e7-a3c9-221caa197ad2	${organizationScopeConsentText}	consent.screen.text
bef193f9-e920-42e7-a3c9-221caa197ad2	true	include.in.token.scope
\.


--
-- Data for Name: client_scope_client; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.client_scope_client (client_id, scope_id, default_scope) FROM stdin;
822c0def-a413-405f-ada7-901a19f0e14a	663d10f9-9ab7-463a-aade-b824010d8b28	t
822c0def-a413-405f-ada7-901a19f0e14a	d0485413-da03-4c3a-91a5-4219e7551095	t
822c0def-a413-405f-ada7-901a19f0e14a	5cc32155-3949-4283-980d-bc97497c21e8	t
822c0def-a413-405f-ada7-901a19f0e14a	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	t
822c0def-a413-405f-ada7-901a19f0e14a	92bca07a-04d6-4cee-970b-9a36b484c33c	t
822c0def-a413-405f-ada7-901a19f0e14a	bd40343b-10d5-4eaf-8fd0-6bc5122ff280	t
822c0def-a413-405f-ada7-901a19f0e14a	ad44ff53-1040-4b35-9e8f-ede27da2a5cb	f
822c0def-a413-405f-ada7-901a19f0e14a	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	f
822c0def-a413-405f-ada7-901a19f0e14a	d5b5a828-823a-42ee-a4e3-c3532c896426	f
822c0def-a413-405f-ada7-901a19f0e14a	1ee89a9b-aa2c-4a44-896c-a942160b8976	f
822c0def-a413-405f-ada7-901a19f0e14a	bef193f9-e920-42e7-a3c9-221caa197ad2	f
d3fd2493-8a37-4cf7-9c70-b05841741543	663d10f9-9ab7-463a-aade-b824010d8b28	t
d3fd2493-8a37-4cf7-9c70-b05841741543	d0485413-da03-4c3a-91a5-4219e7551095	t
d3fd2493-8a37-4cf7-9c70-b05841741543	5cc32155-3949-4283-980d-bc97497c21e8	t
d3fd2493-8a37-4cf7-9c70-b05841741543	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	t
d3fd2493-8a37-4cf7-9c70-b05841741543	92bca07a-04d6-4cee-970b-9a36b484c33c	t
d3fd2493-8a37-4cf7-9c70-b05841741543	bd40343b-10d5-4eaf-8fd0-6bc5122ff280	t
d3fd2493-8a37-4cf7-9c70-b05841741543	ad44ff53-1040-4b35-9e8f-ede27da2a5cb	f
d3fd2493-8a37-4cf7-9c70-b05841741543	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	f
d3fd2493-8a37-4cf7-9c70-b05841741543	d5b5a828-823a-42ee-a4e3-c3532c896426	f
d3fd2493-8a37-4cf7-9c70-b05841741543	1ee89a9b-aa2c-4a44-896c-a942160b8976	f
d3fd2493-8a37-4cf7-9c70-b05841741543	bef193f9-e920-42e7-a3c9-221caa197ad2	f
99c140d7-b340-4425-9fde-800f2f552715	663d10f9-9ab7-463a-aade-b824010d8b28	t
99c140d7-b340-4425-9fde-800f2f552715	5cc32155-3949-4283-980d-bc97497c21e8	t
99c140d7-b340-4425-9fde-800f2f552715	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	t
99c140d7-b340-4425-9fde-800f2f552715	92bca07a-04d6-4cee-970b-9a36b484c33c	t
99c140d7-b340-4425-9fde-800f2f552715	bd40343b-10d5-4eaf-8fd0-6bc5122ff280	t
99c140d7-b340-4425-9fde-800f2f552715	ad44ff53-1040-4b35-9e8f-ede27da2a5cb	f
99c140d7-b340-4425-9fde-800f2f552715	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	f
99c140d7-b340-4425-9fde-800f2f552715	d5b5a828-823a-42ee-a4e3-c3532c896426	f
99c140d7-b340-4425-9fde-800f2f552715	1ee89a9b-aa2c-4a44-896c-a942160b8976	f
99c140d7-b340-4425-9fde-800f2f552715	bef193f9-e920-42e7-a3c9-221caa197ad2	f
ce701a82-9534-4f28-8501-8af5485fdbef	663d10f9-9ab7-463a-aade-b824010d8b28	t
ce701a82-9534-4f28-8501-8af5485fdbef	d0485413-da03-4c3a-91a5-4219e7551095	t
ce701a82-9534-4f28-8501-8af5485fdbef	5cc32155-3949-4283-980d-bc97497c21e8	t
ce701a82-9534-4f28-8501-8af5485fdbef	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	t
ce701a82-9534-4f28-8501-8af5485fdbef	92bca07a-04d6-4cee-970b-9a36b484c33c	t
ce701a82-9534-4f28-8501-8af5485fdbef	bd40343b-10d5-4eaf-8fd0-6bc5122ff280	t
ce701a82-9534-4f28-8501-8af5485fdbef	ad44ff53-1040-4b35-9e8f-ede27da2a5cb	f
ce701a82-9534-4f28-8501-8af5485fdbef	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	f
ce701a82-9534-4f28-8501-8af5485fdbef	d5b5a828-823a-42ee-a4e3-c3532c896426	f
ce701a82-9534-4f28-8501-8af5485fdbef	1ee89a9b-aa2c-4a44-896c-a942160b8976	f
ce701a82-9534-4f28-8501-8af5485fdbef	bef193f9-e920-42e7-a3c9-221caa197ad2	f
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	663d10f9-9ab7-463a-aade-b824010d8b28	t
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	d0485413-da03-4c3a-91a5-4219e7551095	t
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	5cc32155-3949-4283-980d-bc97497c21e8	t
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	t
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	92bca07a-04d6-4cee-970b-9a36b484c33c	t
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	bd40343b-10d5-4eaf-8fd0-6bc5122ff280	t
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	ad44ff53-1040-4b35-9e8f-ede27da2a5cb	f
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	f
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	d5b5a828-823a-42ee-a4e3-c3532c896426	f
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	1ee89a9b-aa2c-4a44-896c-a942160b8976	f
0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	bef193f9-e920-42e7-a3c9-221caa197ad2	f
d1318f74-13c0-4a48-a30a-08b679c21f15	663d10f9-9ab7-463a-aade-b824010d8b28	t
d1318f74-13c0-4a48-a30a-08b679c21f15	d0485413-da03-4c3a-91a5-4219e7551095	t
d1318f74-13c0-4a48-a30a-08b679c21f15	5cc32155-3949-4283-980d-bc97497c21e8	t
d1318f74-13c0-4a48-a30a-08b679c21f15	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	t
d1318f74-13c0-4a48-a30a-08b679c21f15	92bca07a-04d6-4cee-970b-9a36b484c33c	t
d1318f74-13c0-4a48-a30a-08b679c21f15	bd40343b-10d5-4eaf-8fd0-6bc5122ff280	t
d1318f74-13c0-4a48-a30a-08b679c21f15	ad44ff53-1040-4b35-9e8f-ede27da2a5cb	f
d1318f74-13c0-4a48-a30a-08b679c21f15	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	f
d1318f74-13c0-4a48-a30a-08b679c21f15	d5b5a828-823a-42ee-a4e3-c3532c896426	f
d1318f74-13c0-4a48-a30a-08b679c21f15	1ee89a9b-aa2c-4a44-896c-a942160b8976	f
d1318f74-13c0-4a48-a30a-08b679c21f15	bef193f9-e920-42e7-a3c9-221caa197ad2	f
99c140d7-b340-4425-9fde-800f2f552715	2dfed20c-b325-4d0c-a0db-834457fb7bd5	t
99c140d7-b340-4425-9fde-800f2f552715	d0485413-da03-4c3a-91a5-4219e7551095	t
7866504f-df5b-40ad-8128-3ac0e63fc85a	663d10f9-9ab7-463a-aade-b824010d8b28	t
7866504f-df5b-40ad-8128-3ac0e63fc85a	d0485413-da03-4c3a-91a5-4219e7551095	t
7866504f-df5b-40ad-8128-3ac0e63fc85a	5cc32155-3949-4283-980d-bc97497c21e8	t
7866504f-df5b-40ad-8128-3ac0e63fc85a	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	t
7866504f-df5b-40ad-8128-3ac0e63fc85a	92bca07a-04d6-4cee-970b-9a36b484c33c	t
7866504f-df5b-40ad-8128-3ac0e63fc85a	bd40343b-10d5-4eaf-8fd0-6bc5122ff280	t
7866504f-df5b-40ad-8128-3ac0e63fc85a	ad44ff53-1040-4b35-9e8f-ede27da2a5cb	f
7866504f-df5b-40ad-8128-3ac0e63fc85a	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	f
7866504f-df5b-40ad-8128-3ac0e63fc85a	d5b5a828-823a-42ee-a4e3-c3532c896426	f
7866504f-df5b-40ad-8128-3ac0e63fc85a	1ee89a9b-aa2c-4a44-896c-a942160b8976	f
7866504f-df5b-40ad-8128-3ac0e63fc85a	bef193f9-e920-42e7-a3c9-221caa197ad2	f
7866504f-df5b-40ad-8128-3ac0e63fc85a	2dfed20c-b325-4d0c-a0db-834457fb7bd5	t
\.


--
-- Data for Name: client_scope_role_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.client_scope_role_mapping (scope_id, role_id) FROM stdin;
ad44ff53-1040-4b35-9e8f-ede27da2a5cb	1db8d9c4-753a-4f46-a35d-39b0efe93bcb
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.component (id, name, parent_id, provider_id, provider_type, realm_id, sub_type) FROM stdin;
d16a1050-1b89-4dfc-9986-b97b47bf2e51	Trusted Hosts	2522a2b1-50e1-43f3-947e-15bab8e65d6a	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	anonymous
73b742bc-c89d-4c00-b761-75003e192ba9	Consent Required	2522a2b1-50e1-43f3-947e-15bab8e65d6a	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	anonymous
b5642465-0f08-4ec0-ae99-4e6b9a8cc944	Full Scope Disabled	2522a2b1-50e1-43f3-947e-15bab8e65d6a	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	anonymous
88afd997-fbed-4e97-b32b-1cd17998b3c1	Max Clients Limit	2522a2b1-50e1-43f3-947e-15bab8e65d6a	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	anonymous
80f0953f-0917-43aa-bea4-84df7ccbb7c9	Allowed Protocol Mapper Types	2522a2b1-50e1-43f3-947e-15bab8e65d6a	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	anonymous
fa6103e0-76c4-43b2-bc25-4cf7b537fbcd	Allowed Client Scopes	2522a2b1-50e1-43f3-947e-15bab8e65d6a	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	anonymous
b1b5daa9-bacb-47d2-bd28-9d9ca8326835	Allowed Protocol Mapper Types	2522a2b1-50e1-43f3-947e-15bab8e65d6a	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	authenticated
00ab6ee6-914b-4f80-af07-f5b9858a705b	Allowed Client Scopes	2522a2b1-50e1-43f3-947e-15bab8e65d6a	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	authenticated
3ab05e47-0847-46c5-ad7b-41ededd6eaa5	rsa-generated	2522a2b1-50e1-43f3-947e-15bab8e65d6a	rsa-generated	org.keycloak.keys.KeyProvider	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N
1a58f59b-0c95-4d5e-a2a2-e903c95859ac	rsa-enc-generated	2522a2b1-50e1-43f3-947e-15bab8e65d6a	rsa-enc-generated	org.keycloak.keys.KeyProvider	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N
77336f6a-d518-4740-8cf1-8d943fdd6944	hmac-generated-hs512	2522a2b1-50e1-43f3-947e-15bab8e65d6a	hmac-generated	org.keycloak.keys.KeyProvider	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N
24b4fbb0-1d2d-42fb-ab64-43118bd9c6e0	aes-generated	2522a2b1-50e1-43f3-947e-15bab8e65d6a	aes-generated	org.keycloak.keys.KeyProvider	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N
6dcd6f7d-5df5-46af-9bb4-f0b14cf6fc29	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	declarative-user-profile	org.keycloak.userprofile.UserProfileProvider	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N
\.


--
-- Data for Name: component_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.component_config (id, component_id, name, value) FROM stdin;
2ef69f21-454d-4238-94d7-1bfc08d9a538	00ab6ee6-914b-4f80-af07-f5b9858a705b	allow-default-scopes	true
ff1f974e-a628-4380-8e24-5bb7d8c7bc8f	fa6103e0-76c4-43b2-bc25-4cf7b537fbcd	allow-default-scopes	true
a297d6c9-7a01-4304-aaa7-9169405917c7	80f0953f-0917-43aa-bea4-84df7ccbb7c9	allowed-protocol-mapper-types	saml-user-property-mapper
ec62cb0c-9f93-4775-bd41-b59704922420	80f0953f-0917-43aa-bea4-84df7ccbb7c9	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
bdecb176-a141-42a6-b8db-56ce85159625	80f0953f-0917-43aa-bea4-84df7ccbb7c9	allowed-protocol-mapper-types	oidc-full-name-mapper
45ce7aa9-450c-411b-a9c1-f61b6b7d4e38	80f0953f-0917-43aa-bea4-84df7ccbb7c9	allowed-protocol-mapper-types	saml-role-list-mapper
22d261b7-5027-4d5a-b813-f8d6794336e9	80f0953f-0917-43aa-bea4-84df7ccbb7c9	allowed-protocol-mapper-types	saml-user-attribute-mapper
2bc2cd6f-2361-4915-a382-6ec9bb2913c1	80f0953f-0917-43aa-bea4-84df7ccbb7c9	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
8686a57c-4ae4-411d-a0a5-0751733619fa	80f0953f-0917-43aa-bea4-84df7ccbb7c9	allowed-protocol-mapper-types	oidc-address-mapper
affd3497-7c5a-4a37-8b7d-b28e27485510	80f0953f-0917-43aa-bea4-84df7ccbb7c9	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
61235742-edbd-4403-85ce-8b31959ad236	88afd997-fbed-4e97-b32b-1cd17998b3c1	max-clients	200
4596571d-9c11-4921-bdfd-2284ad8ccf79	b1b5daa9-bacb-47d2-bd28-9d9ca8326835	allowed-protocol-mapper-types	saml-user-attribute-mapper
18413919-8683-4b30-92ba-8f5bbda01341	b1b5daa9-bacb-47d2-bd28-9d9ca8326835	allowed-protocol-mapper-types	saml-user-property-mapper
9e6595c0-56c4-46aa-9912-71740714e0fe	b1b5daa9-bacb-47d2-bd28-9d9ca8326835	allowed-protocol-mapper-types	oidc-full-name-mapper
94eb3aac-b76a-4db2-bbd6-f596cd696c46	b1b5daa9-bacb-47d2-bd28-9d9ca8326835	allowed-protocol-mapper-types	oidc-address-mapper
eb7f630e-5840-4436-8e01-455db7c8f1c7	b1b5daa9-bacb-47d2-bd28-9d9ca8326835	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
2b4eff1a-153d-41f9-a6fd-5a423a11d181	b1b5daa9-bacb-47d2-bd28-9d9ca8326835	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
51777bd9-f57d-47cc-81b4-d43ba6955a3c	b1b5daa9-bacb-47d2-bd28-9d9ca8326835	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
55f47cc1-edee-492b-a6c3-79fd361ac6e4	b1b5daa9-bacb-47d2-bd28-9d9ca8326835	allowed-protocol-mapper-types	saml-role-list-mapper
4dbb0caf-529e-4472-8077-cbe962415021	d16a1050-1b89-4dfc-9986-b97b47bf2e51	client-uris-must-match	true
da0961c5-3cfe-4655-93ab-d46f5860de52	d16a1050-1b89-4dfc-9986-b97b47bf2e51	host-sending-registration-request-must-match	true
f9d68327-d6f1-4101-85de-aa6a789358b4	77336f6a-d518-4740-8cf1-8d943fdd6944	kid	8b420a31-63fc-44cf-a3f4-66af9c5eb88a
1ac5dec4-b729-4197-96fe-2e521c359c06	77336f6a-d518-4740-8cf1-8d943fdd6944	priority	100
2f786458-fb0e-4522-a0ca-2efcf899e749	77336f6a-d518-4740-8cf1-8d943fdd6944	secret	SsMLzBm_AyuS1w_p26SpnHPyRm51sVB9yxh9BL1Wb75t-Qh7VyTRLBGjYijr0sh_xUftlW9EgOsbdW3ryYm9AzBV60aRui09Tqyi_3oZ2z6BCANdyNyzQAtM3Le20b60v8pQnJng4pSegDexRcVdfdjQ101zebyYtWL_w7Udc7w
07a6945d-8908-45d0-a675-d37e7afd1ed8	77336f6a-d518-4740-8cf1-8d943fdd6944	algorithm	HS512
149d7c3a-96cd-4e22-b8d6-4eec5df07e5a	1a58f59b-0c95-4d5e-a2a2-e903c95859ac	keyUse	ENC
98dfe2d1-424a-47dc-b224-170c20196bca	1a58f59b-0c95-4d5e-a2a2-e903c95859ac	certificate	MIICmzCCAYMCBgGZ5695IjANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjUxMDE1MTE0MjQwWhcNMzUxMDE1MTE0NDIwWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDNrE58gQjaTgX6mSJI5Enppz4D1p8TrQDgrk7fXqwbK2jMPo8Lp3jdLWW2kBjELwJXeviOOTb+OQ7cmJoSWPdMab9XT8v0qC+mE7lcIK0lWE3SH99MlvFAxA8Vq96STEEj6TUeObnuQl+6Nx3C8I/3OIWKgnBfdmTcNLAEZZURaFENYuczuZZ0p0KAYeTiSbJxuDdveaxz7IEty2mUa6xxyMyOp2FmANE2VSObsR2hhc+Ljf5gttxBXtfMEhaW5di1nYhlV5Lm9VqK8ptEIAPwzEch+lqUOWG+Y8FxysNt2OUL9yObDUF1zoWJov5hJ6GcdkDNZVU6C6HHWhUWUo0xAgMBAAEwDQYJKoZIhvcNAQELBQADggEBALeom4xtklZ5gd0Jc9fP/IlJjS4KocXdUUxdFhDhiabFeorjfNXrqXOKmmNQUvIyrPv8oYUITcpgb8erzqvC2Atz7it7UAbTTCvfisopmchu50GoDe5XnZ+NrKHYYd+65O3UzR7f1QUS9MPeR4o9S/WRifLAjcw7fflq54eMcerTL2mreuushEKleEreghKTGqGxEgfthnQJZ41PLTYitYw8jOfO7cHcZgyYERMaF0Q7wI3R9xctPQ+vONC2tGvGKkfyDb0FSAzFczvuLb6JFFg6xxpkWNRiVpgssQfSyRJY06xjk1LGyyYoT69ODY2P9XJuvMltKv5AO0swFqe0a2E=
5599269e-a3aa-473c-9d71-ba64581dd912	1a58f59b-0c95-4d5e-a2a2-e903c95859ac	privateKey	MIIEpAIBAAKCAQEAzaxOfIEI2k4F+pkiSORJ6ac+A9afE60A4K5O316sGytozD6PC6d43S1ltpAYxC8CV3r4jjk2/jkO3JiaElj3TGm/V0/L9KgvphO5XCCtJVhN0h/fTJbxQMQPFavekkxBI+k1Hjm57kJfujcdwvCP9ziFioJwX3Zk3DSwBGWVEWhRDWLnM7mWdKdCgGHk4kmycbg3b3msc+yBLctplGusccjMjqdhZgDRNlUjm7EdoYXPi43+YLbcQV7XzBIWluXYtZ2IZVeS5vVaivKbRCAD8MxHIfpalDlhvmPBccrDbdjlC/cjmw1Bdc6FiaL+YSehnHZAzWVVOguhx1oVFlKNMQIDAQABAoIBABSOqOej5BUh4FIcdUNQ4hxjFX0/ufW3AJP0iDimV1B95cMoa5bQHfIJhBWb0IXnZvYEegyJJniBjn05GYlE8C7okPHyrWGeX+jbJtdRAs1tRpX+joyyt5lwH0vcumnoCkjDGgaT4YQxiSztLmHaNqGQujROdlnsVQd3krJVzvEGzKWEBNofnPTZ8G+Sv/N5X7grZbLoit9gCD/p7/G2f2QaZFW/vwBqs7IxaggxeXb1T77JnRdEormYEGZg/nJYsWAgLXIIUWggqzwm/PGPKxZ7gy0qC1+yhgJqDDQ+1eEydLSAHNfSI8bLFuRvG1upiXtW55K0k+yE3lqszKNPNMsCgYEA+9IN+6gnJwATGDGhL2j6azxe1p+JNqIBT37E3IPmT/nqJPT91UZaC/9tKzH4lcJBkKhrbCq5gDVG5n3JNGSa2tkq2mBM+9Rm+KrtrsBRaOvXMb0HaAIhG9IKeKDNevP3J4NZsiDecDvnUNFhrSLZwWJQ9R6346By/cHXw6L8Bp8CgYEA0RYtxCBfzYZTW5aYjV42v011IX03B45j+A+WX+aM3FZYxAGxVY9qRMJmwoQeqakH6ovubByPA8Ea+LvBM4K8Vl361cvCBORmCV0W1rmyAxYNJrhH1HgIJOb5D/ss/wCotJr4fRFASF3/6tsTiseUjFg5EvO1S0H8G1WFeLQA6i8CgYEA5fj4UipHz0MvLPouFMkPYR2aEvFRczZSchH4oNZIYCOBJM0kf0CnfauAPGF/fu0PyZc1u2vtStvc6yRWuuVh0plfnF00EQvyGgin1d7GukYfmcni2KaRLAIPFAjufuoMfeZj5NxFqEPcxR7i0CYyrXwnKSQrldLzETGU5ZKUDUsCgYBGErTL4j25V6dgLpx55HVcr8ztqFVlBueiHzc6dL4IqjedmSOhlSr8WFqwHi9YlPm+NZ92RCv6CiHL8sDRTqNoHp8OP/eKRDGeOcCJvjpWpnFJ5MDOCFxSYcZfAMMXw1THEpjs8XclybsXLszh2fmC/55BJ8Q+sKKZVpQg29LiqQKBgQCBudZdfDsNXkmJgJ0+yZ/cWFafOgrz7w4xlDsh8opb2mxeJ/u5tg6zVMHh7YAHwljsmRIgiiqysS8ahXgpRRpdcLD62SSPV0l/sYsPY6IRkl0nBEhd6uSzd9xFWhG5clonKI3UaR0Dr3l7RtamD2TYR6OnjME+4pRaEc/ifuuxZw==
55261fb9-5db5-4aa9-bedb-c6c8d1f45903	1a58f59b-0c95-4d5e-a2a2-e903c95859ac	algorithm	RSA-OAEP
4d3d6c22-cb6b-46db-97ff-8f224ce6b1c0	1a58f59b-0c95-4d5e-a2a2-e903c95859ac	priority	100
56ca975e-5c1e-4b71-838d-fdf58ab26160	3ab05e47-0847-46c5-ad7b-41ededd6eaa5	privateKey	MIIEpAIBAAKCAQEAs3IyMAERJvU6KeChviLHB0uSaeJc7ojEEAKUubqAgfNvyDcRhYlpX8dfkOcEUbw0tO/yXHuhtrCGkkBpyIuMkgNlPOcrIQY9KTK9TgAsb7F5xnxKcg7DC37850U4sI4OVBre1Y+ojqojA2xBhB3CjmWaUtA9qrlwZphFTpr9RqDqdp64D0LF6mX7AkbSBJlBYSZAmQMtxBmV1B7tWhvZnxB9Wmh3eF+so7u9caOxpOYmXE44Ok/eve2EmGByMFz6/k4AdRpYTEuzQjGhACIReCu9oKOoGFL6T7rAYSAZT3Yo9H9yNPWqrGmEt1j3IXlcfHmSAV4KS62BeY8ppDX46wIDAQABAoIBACZXD18PU01aFufMrXI09wZZgLpr2YRDp3RuQL2gGgXp7+e80FsSPXfv3rzUhyk5h59jJwrYhXhFpjMIXTGyk0G9RPuqvp3LOfUb+LN6OQRSTJz6gGN3QPgrVTjKou6iI3CX3yWNEKwOsPVxw1vJigCnt73a7VoVTe01upUta1EUAvb4XtidhRVJ0ySVPo7u3JL9u1osfVzk7uoqCLnvTKJFUfZ/9+JEy4HQKo56niaqdU/rB2W6emTjCa1/j68JoLQIcPDTaHpjikTyTfaQHUGAFZCuL4wxtrHmEOptEpkzsFtn6T2mpY/iYcrflUnniMwwnAHqQs6j/oldFCxNCy0CgYEA+OmnhsYEtaMTRXapULSZPH/xtqRk31seB2cnQkLcQzgEv2h8m8Ic3R/FJcJZq1OKzqJcEDKLm22Ok5tsG2v04EBOghaHNw5Y8VVcqhW1zuMNlLIq414/AyyL9hxcfsXN3nPXDMf4MJ8LEjvVmjgHaU1/yIJriP8oCz/oDSBDtA8CgYEAuI4xi1iAaC8jGAHXPQ+VVOnwSxoqDoGDJHohuqZV5A5SbUI6beA08DYvwUZO4qvFSjVwCM7wHHUCmHRmAbl+1+q5MF8iljB7oE3PTsiZVGMzVGuvBHiH13FYOLaJNYWs+rmxswPsRzn4UXWu7VM/txgY0WS5sDRPUwG01OoWIWUCgYA0HegfxfJSfbSZRMEqzsWO71MS5NTiF1fwQN7tdsZbpK0JzrzYeApHGlHTGCBiSPm1TjVshsjIYGYL7ezyyOXa1lwZzNG+61ggH7lpO2Z3zP6Y9b0ZIjCbX4Zmn4WWXaA/RY3vdSc4NvTAepq80aJrn/hzaStO4e3mfRj/NpQ2bQKBgQCQGlcNGtmIf+XDBQzvHI/ZcaCyw4c7NF0VylczylUecSBjN2cwC/TJ60V2gFNwIfc2bmtANXS+g463Q4uypQgpx5k5786tvsWI8biBJ5ixj5OwwzQxJXvL+TCNd9nC9NGQJBZgVRvUsar20BD6Nhp1+FbIiQGEWBPoGFj+Npl5EQKBgQDcFPP6Bv8HAq7GPiygi8eHd/Y91yblqJLu7997gcA0yzvpU37d6b1PbecZsFLeaZL3s7SpcmzHt/wmiPK95CO+fgVjrTt+zUEu4X/WOXOLqCgIcUSZiRWVktCCrWjTIJN0NwDxEik2AgRe4Zn+eMGM5YCgT3xUizbGfP5KITVtbg==
0360c254-a5c8-47d6-8132-e6b34f8210cc	3ab05e47-0847-46c5-ad7b-41ededd6eaa5	priority	100
646ea879-19c2-49e5-bcc2-23666f18b2a7	3ab05e47-0847-46c5-ad7b-41ededd6eaa5	certificate	MIICmzCCAYMCBgGZ5694ODANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjUxMDE1MTE0MjQwWhcNMzUxMDE1MTE0NDIwWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCzcjIwAREm9Top4KG+IscHS5Jp4lzuiMQQApS5uoCB82/INxGFiWlfx1+Q5wRRvDS07/Jce6G2sIaSQGnIi4ySA2U85yshBj0pMr1OACxvsXnGfEpyDsMLfvznRTiwjg5UGt7Vj6iOqiMDbEGEHcKOZZpS0D2quXBmmEVOmv1GoOp2nrgPQsXqZfsCRtIEmUFhJkCZAy3EGZXUHu1aG9mfEH1aaHd4X6yju71xo7Gk5iZcTjg6T9697YSYYHIwXPr+TgB1GlhMS7NCMaEAIhF4K72go6gYUvpPusBhIBlPdij0f3I09aqsaYS3WPcheVx8eZIBXgpLrYF5jymkNfjrAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAKPbbaxYWv6xoQPr3SH8dukX+UGuz0+6EWMZoE+8G6Wi11LFSRM1zk3ewz7VsHJk3ueL2vxumbJ5XALdigACMjYd+32f1kJYIjQK9BmYnHsJsp3kzcBEczXgl1v8D5xZTYLMdklZgJRUW1AZB7pKLg04129Sh8mKX66pFWCIykSCxOCvyUoUsH7Uerx/AnoPlXO9HpNx6deditJyuCFmPXpGOqLx6f8vw6OTe4L6v8h/NWfcDy7Xtr8sE23V8L7QfiRhL7sX55MX0rl4zr1F80M8C7mCWu664jER6/1YncP6/nAlS12wd+witLKvsFZaWmQ0wLmzqTA0sATIcDwZ6so=
e09aee17-6deb-436b-882c-b1a84a948bbd	3ab05e47-0847-46c5-ad7b-41ededd6eaa5	keyUse	SIG
b6ee48db-cfc4-4b78-9af1-74767fee3097	24b4fbb0-1d2d-42fb-ab64-43118bd9c6e0	kid	caa54147-75c7-4688-9499-8833beded8e6
2453efe8-afc6-499c-9e35-cf7a1188ddeb	24b4fbb0-1d2d-42fb-ab64-43118bd9c6e0	secret	cv7t6B-QwHkt7mg-7sQFEw
543801f2-3d3d-4370-98ac-25036fbbcd30	24b4fbb0-1d2d-42fb-ab64-43118bd9c6e0	priority	100
263010ed-d909-4e8e-a74e-6bac041a9f17	6dcd6f7d-5df5-46af-9bb4-f0b14cf6fc29	kc.user.profile.config	{"attributes":[{"name":"username","displayName":"${username}","validations":{"length":{"min":3,"max":255},"username-prohibited-characters":{},"up-username-not-idn-homograph":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"email","displayName":"${email}","validations":{"email":{},"length":{"max":255}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false}],"groups":[{"name":"user-metadata","displayHeader":"User metadata","displayDescription":"Attributes, which refer to user metadata"}]}
\.


--
-- Data for Name: composite_role; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.composite_role (composite, child_role) FROM stdin;
7226339e-3ab6-47c4-95ec-3737455f18b9	5e3e0557-02af-4ad5-9ad6-3499e53b1623
7226339e-3ab6-47c4-95ec-3737455f18b9	6568a4c5-5ab9-4e68-a5bb-45106d5a281c
7226339e-3ab6-47c4-95ec-3737455f18b9	71ad2f48-f902-4f9d-9149-80a5ef7d5a8f
7226339e-3ab6-47c4-95ec-3737455f18b9	69ef72dd-84a6-49c9-ba40-cc460863e8e3
7226339e-3ab6-47c4-95ec-3737455f18b9	34cfd536-9213-4917-917c-751b4e343316
7226339e-3ab6-47c4-95ec-3737455f18b9	914e9dbd-a8ab-4ec3-bb2d-cf3d2d2ab404
7226339e-3ab6-47c4-95ec-3737455f18b9	f627261d-4dfd-45b2-b93c-73bbfbb725de
7226339e-3ab6-47c4-95ec-3737455f18b9	2859f9ff-f766-4e0d-8ab4-1e8117c10d60
7226339e-3ab6-47c4-95ec-3737455f18b9	53f729d3-094b-436a-a549-376a0febb00c
7226339e-3ab6-47c4-95ec-3737455f18b9	1ea03edf-1ef8-4239-b3b9-6987ef11c040
7226339e-3ab6-47c4-95ec-3737455f18b9	9c9cc5df-7638-43e0-91d7-3684b7a2c098
7226339e-3ab6-47c4-95ec-3737455f18b9	5fd7b8a9-6867-47b3-a107-8f3d66634966
7226339e-3ab6-47c4-95ec-3737455f18b9	89283bce-e881-443a-b93c-56f35dd79f28
7226339e-3ab6-47c4-95ec-3737455f18b9	2b5c705e-b163-43ab-b604-4444a0944c23
7226339e-3ab6-47c4-95ec-3737455f18b9	f26ca79c-71fa-4e59-bb48-2fc1bf425c0f
7226339e-3ab6-47c4-95ec-3737455f18b9	ef15f1cb-06a9-4ea0-8ccd-b43b1b2b8569
7226339e-3ab6-47c4-95ec-3737455f18b9	eaf15d14-fb80-4854-aaa5-ee76eb150396
7226339e-3ab6-47c4-95ec-3737455f18b9	6ad38393-1e9f-4dec-860c-bfebffcc5a25
0794b2aa-e7c5-4511-b7e4-b70e017ee375	3e8300fa-4e66-4a29-9717-c4cfd540c074
34cfd536-9213-4917-917c-751b4e343316	ef15f1cb-06a9-4ea0-8ccd-b43b1b2b8569
69ef72dd-84a6-49c9-ba40-cc460863e8e3	6ad38393-1e9f-4dec-860c-bfebffcc5a25
69ef72dd-84a6-49c9-ba40-cc460863e8e3	f26ca79c-71fa-4e59-bb48-2fc1bf425c0f
0794b2aa-e7c5-4511-b7e4-b70e017ee375	dff8d26f-16bb-4c9d-9295-8800a2a6adf4
dff8d26f-16bb-4c9d-9295-8800a2a6adf4	d30a45df-bf49-4775-aab8-ca17a3e10efd
1aa18df7-e256-4f7c-9dca-95a28ed6b805	036578e3-eb6f-4856-bb02-28584bebb14d
7226339e-3ab6-47c4-95ec-3737455f18b9	ff628bb2-ece9-4e45-8b52-550b073b051b
0794b2aa-e7c5-4511-b7e4-b70e017ee375	1db8d9c4-753a-4f46-a35d-39b0efe93bcb
0794b2aa-e7c5-4511-b7e4-b70e017ee375	e79eb703-2a61-426b-a40b-8e890e048f58
\.


--
-- Data for Name: credential; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.credential (id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority, version) FROM stdin;
c539a0ab-a0e3-4fbf-a74c-232fa3c3217d	\N	password	24901224-b171-470a-9f02-ce1f78be5622	1760528661282	\N	{"value":"oHkUqNEjZANcgD3Lz3Ay6EwgkosxpifQEywIDoRJUAo=","salt":"RqNLMt/LmMzVrvJ3mtk2XQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10	0
5e2b2799-be96-4c46-8ec0-11d14dd606fb	\N	password	564dccad-9599-4053-a861-83d405a5762d	1760529209512	My password	{"value":"xk3VubwMWdeGBPWc2AJ3VdTUj3m6NdAEdVmKwWfCEBs=","salt":"Bk48CBvZGEu8GaZ/t9GLSQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10	5
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/jpa-changelog-1.0.0.Final.xml	2025-10-15 11:44:13.019613	1	EXECUTED	9:6f1016664e21e16d26517a4418f5e3df	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.33.0	\N	\N	0528649527
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/db2-jpa-changelog-1.0.0.Final.xml	2025-10-15 11:44:13.030635	2	MARK_RAN	9:828775b1596a07d1200ba1d49e5e3941	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.33.0	\N	\N	0528649527
1.1.0.Beta1	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Beta1.xml	2025-10-15 11:44:13.060778	3	EXECUTED	9:5f090e44a7d595883c1fb61f4b41fd38	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...		\N	4.33.0	\N	\N	0528649527
1.1.0.Final	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Final.xml	2025-10-15 11:44:13.066048	4	EXECUTED	9:c07e577387a3d2c04d1adc9aaad8730e	renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY		\N	4.33.0	\N	\N	0528649527
1.2.0.Beta1	psilva@redhat.com	META-INF/jpa-changelog-1.2.0.Beta1.xml	2025-10-15 11:44:13.142743	5	EXECUTED	9:b68ce996c655922dbcd2fe6b6ae72686	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.33.0	\N	\N	0528649527
1.2.0.Beta1	psilva@redhat.com	META-INF/db2-jpa-changelog-1.2.0.Beta1.xml	2025-10-15 11:44:13.146208	6	MARK_RAN	9:543b5c9989f024fe35c6f6c5a97de88e	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.33.0	\N	\N	0528649527
1.2.0.RC1	bburke@redhat.com	META-INF/jpa-changelog-1.2.0.CR1.xml	2025-10-15 11:44:13.205938	7	EXECUTED	9:765afebbe21cf5bbca048e632df38336	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.33.0	\N	\N	0528649527
1.2.0.RC1	bburke@redhat.com	META-INF/db2-jpa-changelog-1.2.0.CR1.xml	2025-10-15 11:44:13.209067	8	MARK_RAN	9:db4a145ba11a6fdaefb397f6dbf829a1	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.33.0	\N	\N	0528649527
1.2.0.Final	keycloak	META-INF/jpa-changelog-1.2.0.Final.xml	2025-10-15 11:44:13.21778	9	EXECUTED	9:9d05c7be10cdb873f8bcb41bc3a8ab23	update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT		\N	4.33.0	\N	\N	0528649527
1.3.0	bburke@redhat.com	META-INF/jpa-changelog-1.3.0.xml	2025-10-15 11:44:13.270693	10	EXECUTED	9:18593702353128d53111f9b1ff0b82b8	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...		\N	4.33.0	\N	\N	0528649527
1.4.0	bburke@redhat.com	META-INF/jpa-changelog-1.4.0.xml	2025-10-15 11:44:13.293978	11	EXECUTED	9:6122efe5f090e41a85c0f1c9e52cbb62	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.33.0	\N	\N	0528649527
1.4.0	bburke@redhat.com	META-INF/db2-jpa-changelog-1.4.0.xml	2025-10-15 11:44:13.295725	12	MARK_RAN	9:e1ff28bf7568451453f844c5d54bb0b5	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.33.0	\N	\N	0528649527
1.5.0	bburke@redhat.com	META-INF/jpa-changelog-1.5.0.xml	2025-10-15 11:44:13.304026	13	EXECUTED	9:7af32cd8957fbc069f796b61217483fd	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.33.0	\N	\N	0528649527
1.6.1_from15	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-10-15 11:44:13.313125	14	EXECUTED	9:6005e15e84714cd83226bf7879f54190	addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...		\N	4.33.0	\N	\N	0528649527
1.6.1_from16-pre	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-10-15 11:44:13.313845	15	MARK_RAN	9:bf656f5a2b055d07f314431cae76f06c	delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
1.6.1_from16	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-10-15 11:44:13.314809	16	MARK_RAN	9:f8dadc9284440469dcf71e25ca6ab99b	dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...		\N	4.33.0	\N	\N	0528649527
1.6.1	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-10-15 11:44:13.315744	17	EXECUTED	9:d41d8cd98f00b204e9800998ecf8427e	empty		\N	4.33.0	\N	\N	0528649527
1.7.0	bburke@redhat.com	META-INF/jpa-changelog-1.7.0.xml	2025-10-15 11:44:13.336732	18	EXECUTED	9:3368ff0be4c2855ee2dd9ca813b38d8e	createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...		\N	4.33.0	\N	\N	0528649527
1.8.0	mposolda@redhat.com	META-INF/jpa-changelog-1.8.0.xml	2025-10-15 11:44:13.369234	19	EXECUTED	9:8ac2fb5dd030b24c0570a763ed75ed20	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.33.0	\N	\N	0528649527
1.8.0-2	keycloak	META-INF/jpa-changelog-1.8.0.xml	2025-10-15 11:44:13.372034	20	EXECUTED	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.33.0	\N	\N	0528649527
22.0.5-24031	keycloak	META-INF/jpa-changelog-22.0.0.xml	2025-10-15 11:44:15.728256	119	MARK_RAN	9:a60d2d7b315ec2d3eba9e2f145f9df28	customChange		\N	4.33.0	\N	\N	0528649527
1.8.0	mposolda@redhat.com	META-INF/db2-jpa-changelog-1.8.0.xml	2025-10-15 11:44:13.37414	21	MARK_RAN	9:831e82914316dc8a57dc09d755f23c51	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.33.0	\N	\N	0528649527
1.8.0-2	keycloak	META-INF/db2-jpa-changelog-1.8.0.xml	2025-10-15 11:44:13.375827	22	MARK_RAN	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.33.0	\N	\N	0528649527
1.9.0	mposolda@redhat.com	META-INF/jpa-changelog-1.9.0.xml	2025-10-15 11:44:13.415312	23	EXECUTED	9:bc3d0f9e823a69dc21e23e94c7a94bb1	update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...		\N	4.33.0	\N	\N	0528649527
1.9.1	keycloak	META-INF/jpa-changelog-1.9.1.xml	2025-10-15 11:44:13.418655	24	EXECUTED	9:c9999da42f543575ab790e76439a2679	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.33.0	\N	\N	0528649527
1.9.1	keycloak	META-INF/db2-jpa-changelog-1.9.1.xml	2025-10-15 11:44:13.420018	25	MARK_RAN	9:0d6c65c6f58732d81569e77b10ba301d	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.33.0	\N	\N	0528649527
1.9.2	keycloak	META-INF/jpa-changelog-1.9.2.xml	2025-10-15 11:44:13.696022	26	EXECUTED	9:fc576660fc016ae53d2d4778d84d86d0	createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...		\N	4.33.0	\N	\N	0528649527
authz-2.0.0	psilva@redhat.com	META-INF/jpa-changelog-authz-2.0.0.xml	2025-10-15 11:44:13.727224	27	EXECUTED	9:43ed6b0da89ff77206289e87eaa9c024	createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...		\N	4.33.0	\N	\N	0528649527
authz-2.5.1	psilva@redhat.com	META-INF/jpa-changelog-authz-2.5.1.xml	2025-10-15 11:44:13.728667	28	EXECUTED	9:44bae577f551b3738740281eceb4ea70	update tableName=RESOURCE_SERVER_POLICY		\N	4.33.0	\N	\N	0528649527
2.1.0-KEYCLOAK-5461	bburke@redhat.com	META-INF/jpa-changelog-2.1.0.xml	2025-10-15 11:44:13.756267	29	EXECUTED	9:bd88e1f833df0420b01e114533aee5e8	createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...		\N	4.33.0	\N	\N	0528649527
2.2.0	bburke@redhat.com	META-INF/jpa-changelog-2.2.0.xml	2025-10-15 11:44:13.77082	30	EXECUTED	9:a7022af5267f019d020edfe316ef4371	addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...		\N	4.33.0	\N	\N	0528649527
2.3.0	bburke@redhat.com	META-INF/jpa-changelog-2.3.0.xml	2025-10-15 11:44:13.781939	31	EXECUTED	9:fc155c394040654d6a79227e56f5e25a	createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...		\N	4.33.0	\N	\N	0528649527
2.4.0	bburke@redhat.com	META-INF/jpa-changelog-2.4.0.xml	2025-10-15 11:44:13.785954	32	EXECUTED	9:eac4ffb2a14795e5dc7b426063e54d88	customChange		\N	4.33.0	\N	\N	0528649527
2.5.0	bburke@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-10-15 11:44:13.790384	33	EXECUTED	9:54937c05672568c4c64fc9524c1e9462	customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
2.5.0-unicode-oracle	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-10-15 11:44:13.792286	34	MARK_RAN	9:f9753208029f582525ed12011a19d054	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.33.0	\N	\N	0528649527
2.5.0-unicode-other-dbs	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-10-15 11:44:13.814091	35	EXECUTED	9:33d72168746f81f98ae3a1e8e0ca3554	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.33.0	\N	\N	0528649527
2.5.0-duplicate-email-support	slawomir@dabek.name	META-INF/jpa-changelog-2.5.0.xml	2025-10-15 11:44:13.819183	36	EXECUTED	9:61b6d3d7a4c0e0024b0c839da283da0c	addColumn tableName=REALM		\N	4.33.0	\N	\N	0528649527
2.5.0-unique-group-names	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-10-15 11:44:13.824919	37	EXECUTED	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.33.0	\N	\N	0528649527
2.5.1	bburke@redhat.com	META-INF/jpa-changelog-2.5.1.xml	2025-10-15 11:44:13.827003	38	EXECUTED	9:a2b870802540cb3faa72098db5388af3	addColumn tableName=FED_USER_CONSENT		\N	4.33.0	\N	\N	0528649527
3.0.0	bburke@redhat.com	META-INF/jpa-changelog-3.0.0.xml	2025-10-15 11:44:13.828537	39	EXECUTED	9:132a67499ba24bcc54fb5cbdcfe7e4c0	addColumn tableName=IDENTITY_PROVIDER		\N	4.33.0	\N	\N	0528649527
3.2.0-fix	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-10-15 11:44:13.829199	40	MARK_RAN	9:938f894c032f5430f2b0fafb1a243462	addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS		\N	4.33.0	\N	\N	0528649527
3.2.0-fix-with-keycloak-5416	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-10-15 11:44:13.829878	41	MARK_RAN	9:845c332ff1874dc5d35974b0babf3006	dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS		\N	4.33.0	\N	\N	0528649527
3.2.0-fix-offline-sessions	hmlnarik	META-INF/jpa-changelog-3.2.0.xml	2025-10-15 11:44:13.832611	42	EXECUTED	9:fc86359c079781adc577c5a217e4d04c	customChange		\N	4.33.0	\N	\N	0528649527
3.2.0-fixed	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-10-15 11:44:14.879373	43	EXECUTED	9:59a64800e3c0d09b825f8a3b444fa8f4	addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...		\N	4.33.0	\N	\N	0528649527
3.3.0	keycloak	META-INF/jpa-changelog-3.3.0.xml	2025-10-15 11:44:14.881224	44	EXECUTED	9:d48d6da5c6ccf667807f633fe489ce88	addColumn tableName=USER_ENTITY		\N	4.33.0	\N	\N	0528649527
authz-3.4.0.CR1-resource-server-pk-change-part1	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-10-15 11:44:14.882672	45	EXECUTED	9:dde36f7973e80d71fceee683bc5d2951	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE		\N	4.33.0	\N	\N	0528649527
authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-10-15 11:44:14.885067	46	EXECUTED	9:b855e9b0a406b34fa323235a0cf4f640	customChange		\N	4.33.0	\N	\N	0528649527
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-10-15 11:44:14.885652	47	MARK_RAN	9:51abbacd7b416c50c4421a8cabf7927e	dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE		\N	4.33.0	\N	\N	0528649527
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-10-15 11:44:14.933299	48	EXECUTED	9:bdc99e567b3398bac83263d375aad143	addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...		\N	4.33.0	\N	\N	0528649527
authn-3.4.0.CR1-refresh-token-max-reuse	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-10-15 11:44:14.935231	49	EXECUTED	9:d198654156881c46bfba39abd7769e69	addColumn tableName=REALM		\N	4.33.0	\N	\N	0528649527
3.4.0	keycloak	META-INF/jpa-changelog-3.4.0.xml	2025-10-15 11:44:14.954209	50	EXECUTED	9:cfdd8736332ccdd72c5256ccb42335db	addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...		\N	4.33.0	\N	\N	0528649527
3.4.0-KEYCLOAK-5230	hmlnarik@redhat.com	META-INF/jpa-changelog-3.4.0.xml	2025-10-15 11:44:15.083611	51	EXECUTED	9:7c84de3d9bd84d7f077607c1a4dcb714	createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...		\N	4.33.0	\N	\N	0528649527
3.4.1	psilva@redhat.com	META-INF/jpa-changelog-3.4.1.xml	2025-10-15 11:44:15.085612	52	EXECUTED	9:5a6bb36cbefb6a9d6928452c0852af2d	modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
3.4.2	keycloak	META-INF/jpa-changelog-3.4.2.xml	2025-10-15 11:44:15.086796	53	EXECUTED	9:8f23e334dbc59f82e0a328373ca6ced0	update tableName=REALM		\N	4.33.0	\N	\N	0528649527
3.4.2-KEYCLOAK-5172	mkanis@redhat.com	META-INF/jpa-changelog-3.4.2.xml	2025-10-15 11:44:15.087586	54	EXECUTED	9:9156214268f09d970cdf0e1564d866af	update tableName=CLIENT		\N	4.33.0	\N	\N	0528649527
4.0.0-KEYCLOAK-6335	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-10-15 11:44:15.089807	55	EXECUTED	9:db806613b1ed154826c02610b7dbdf74	createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS		\N	4.33.0	\N	\N	0528649527
4.0.0-CLEANUP-UNUSED-TABLE	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-10-15 11:44:15.091938	56	EXECUTED	9:229a041fb72d5beac76bb94a5fa709de	dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING		\N	4.33.0	\N	\N	0528649527
4.0.0-KEYCLOAK-6228	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-10-15 11:44:15.110539	57	EXECUTED	9:079899dade9c1e683f26b2aa9ca6ff04	dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...		\N	4.33.0	\N	\N	0528649527
4.0.0-KEYCLOAK-5579-fixed	mposolda@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-10-15 11:44:15.253664	58	EXECUTED	9:139b79bcbbfe903bb1c2d2a4dbf001d9	dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...		\N	4.33.0	\N	\N	0528649527
authz-4.0.0.CR1	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.CR1.xml	2025-10-15 11:44:15.265021	59	EXECUTED	9:b55738ad889860c625ba2bf483495a04	createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...		\N	4.33.0	\N	\N	0528649527
authz-4.0.0.Beta3	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.Beta3.xml	2025-10-15 11:44:15.267329	60	EXECUTED	9:e0057eac39aa8fc8e09ac6cfa4ae15fe	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY		\N	4.33.0	\N	\N	0528649527
authz-4.2.0.Final	mhajas@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2025-10-15 11:44:15.271218	61	EXECUTED	9:42a33806f3a0443fe0e7feeec821326c	createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...		\N	4.33.0	\N	\N	0528649527
authz-4.2.0.Final-KEYCLOAK-9944	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2025-10-15 11:44:15.273038	62	EXECUTED	9:9968206fca46eecc1f51db9c024bfe56	addPrimaryKey constraintName=CONSTRAINT_RESOUR_URIS_PK, tableName=RESOURCE_URIS		\N	4.33.0	\N	\N	0528649527
4.2.0-KEYCLOAK-6313	wadahiro@gmail.com	META-INF/jpa-changelog-4.2.0.xml	2025-10-15 11:44:15.274151	63	EXECUTED	9:92143a6daea0a3f3b8f598c97ce55c3d	addColumn tableName=REQUIRED_ACTION_PROVIDER		\N	4.33.0	\N	\N	0528649527
4.3.0-KEYCLOAK-7984	wadahiro@gmail.com	META-INF/jpa-changelog-4.3.0.xml	2025-10-15 11:44:15.275098	64	EXECUTED	9:82bab26a27195d889fb0429003b18f40	update tableName=REQUIRED_ACTION_PROVIDER		\N	4.33.0	\N	\N	0528649527
4.6.0-KEYCLOAK-7950	psilva@redhat.com	META-INF/jpa-changelog-4.6.0.xml	2025-10-15 11:44:15.275977	65	EXECUTED	9:e590c88ddc0b38b0ae4249bbfcb5abc3	update tableName=RESOURCE_SERVER_RESOURCE		\N	4.33.0	\N	\N	0528649527
4.6.0-KEYCLOAK-8377	keycloak	META-INF/jpa-changelog-4.6.0.xml	2025-10-15 11:44:15.290988	66	EXECUTED	9:5c1f475536118dbdc38d5d7977950cc0	createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...		\N	4.33.0	\N	\N	0528649527
4.6.0-KEYCLOAK-8555	gideonray@gmail.com	META-INF/jpa-changelog-4.6.0.xml	2025-10-15 11:44:15.303828	67	EXECUTED	9:e7c9f5f9c4d67ccbbcc215440c718a17	createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT		\N	4.33.0	\N	\N	0528649527
4.7.0-KEYCLOAK-1267	sguilhen@redhat.com	META-INF/jpa-changelog-4.7.0.xml	2025-10-15 11:44:15.30547	68	EXECUTED	9:88e0bfdda924690d6f4e430c53447dd5	addColumn tableName=REALM		\N	4.33.0	\N	\N	0528649527
4.7.0-KEYCLOAK-7275	keycloak	META-INF/jpa-changelog-4.7.0.xml	2025-10-15 11:44:15.321206	69	EXECUTED	9:f53177f137e1c46b6a88c59ec1cb5218	renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...		\N	4.33.0	\N	\N	0528649527
4.8.0-KEYCLOAK-8835	sguilhen@redhat.com	META-INF/jpa-changelog-4.8.0.xml	2025-10-15 11:44:15.322989	70	EXECUTED	9:a74d33da4dc42a37ec27121580d1459f	addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM		\N	4.33.0	\N	\N	0528649527
authz-7.0.0-KEYCLOAK-10443	psilva@redhat.com	META-INF/jpa-changelog-authz-7.0.0.xml	2025-10-15 11:44:15.324915	71	EXECUTED	9:fd4ade7b90c3b67fae0bfcfcb42dfb5f	addColumn tableName=RESOURCE_SERVER		\N	4.33.0	\N	\N	0528649527
8.0.0-adding-credential-columns	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-10-15 11:44:15.327152	72	EXECUTED	9:aa072ad090bbba210d8f18781b8cebf4	addColumn tableName=CREDENTIAL; addColumn tableName=FED_USER_CREDENTIAL		\N	4.33.0	\N	\N	0528649527
8.0.0-updating-credential-data-not-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-10-15 11:44:15.329179	73	EXECUTED	9:1ae6be29bab7c2aa376f6983b932be37	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.33.0	\N	\N	0528649527
8.0.0-updating-credential-data-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-10-15 11:44:15.329853	74	MARK_RAN	9:14706f286953fc9a25286dbd8fb30d97	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.33.0	\N	\N	0528649527
8.0.0-credential-cleanup-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-10-15 11:44:15.337347	75	EXECUTED	9:2b9cc12779be32c5b40e2e67711a218b	dropDefaultValue columnName=COUNTER, tableName=CREDENTIAL; dropDefaultValue columnName=DIGITS, tableName=CREDENTIAL; dropDefaultValue columnName=PERIOD, tableName=CREDENTIAL; dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; dropColumn ...		\N	4.33.0	\N	\N	0528649527
8.0.0-resource-tag-support	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-10-15 11:44:15.355495	76	EXECUTED	9:91fa186ce7a5af127a2d7a91ee083cc5	addColumn tableName=MIGRATION_MODEL; createIndex indexName=IDX_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.33.0	\N	\N	0528649527
9.0.0-always-display-client	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-10-15 11:44:15.356845	77	EXECUTED	9:6335e5c94e83a2639ccd68dd24e2e5ad	addColumn tableName=CLIENT		\N	4.33.0	\N	\N	0528649527
9.0.0-drop-constraints-for-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-10-15 11:44:15.357318	78	MARK_RAN	9:6bdb5658951e028bfe16fa0a8228b530	dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5PMT, tableName=RESOURCE_SERVER_PERM_TICKET; dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER_RESOURCE; dropPrimaryKey constraintName=CONSTRAINT_O...		\N	4.33.0	\N	\N	0528649527
9.0.0-increase-column-size-federated-fk	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-10-15 11:44:15.365226	79	EXECUTED	9:d5bc15a64117ccad481ce8792d4c608f	modifyDataType columnName=CLIENT_ID, tableName=FED_USER_CONSENT; modifyDataType columnName=CLIENT_REALM_CONSTRAINT, tableName=KEYCLOAK_ROLE; modifyDataType columnName=OWNER, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=CLIENT_ID, ta...		\N	4.33.0	\N	\N	0528649527
9.0.0-recreate-constraints-after-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-10-15 11:44:15.366133	80	MARK_RAN	9:077cba51999515f4d3e7ad5619ab592c	addNotNullConstraint columnName=CLIENT_ID, tableName=OFFLINE_CLIENT_SESSION; addNotNullConstraint columnName=OWNER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNullConstraint columnName=REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNull...		\N	4.33.0	\N	\N	0528649527
9.0.1-add-index-to-client.client_id	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-10-15 11:44:15.380488	81	EXECUTED	9:be969f08a163bf47c6b9e9ead8ac2afb	createIndex indexName=IDX_CLIENT_ID, tableName=CLIENT		\N	4.33.0	\N	\N	0528649527
9.0.1-KEYCLOAK-12579-drop-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-10-15 11:44:15.381227	82	MARK_RAN	9:6d3bb4408ba5a72f39bd8a0b301ec6e3	dropUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.33.0	\N	\N	0528649527
9.0.1-KEYCLOAK-12579-add-not-null-constraint	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-10-15 11:44:15.38269	83	EXECUTED	9:966bda61e46bebf3cc39518fbed52fa7	addNotNullConstraint columnName=PARENT_GROUP, tableName=KEYCLOAK_GROUP		\N	4.33.0	\N	\N	0528649527
9.0.1-KEYCLOAK-12579-recreate-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-10-15 11:44:15.383111	84	MARK_RAN	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.33.0	\N	\N	0528649527
9.0.1-add-index-to-events	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-10-15 11:44:15.396829	85	EXECUTED	9:7d93d602352a30c0c317e6a609b56599	createIndex indexName=IDX_EVENT_TIME, tableName=EVENT_ENTITY		\N	4.33.0	\N	\N	0528649527
map-remove-ri	keycloak	META-INF/jpa-changelog-11.0.0.xml	2025-10-15 11:44:15.398948	86	EXECUTED	9:71c5969e6cdd8d7b6f47cebc86d37627	dropForeignKeyConstraint baseTableName=REALM, constraintName=FK_TRAF444KK6QRKMS7N56AIWQ5Y; dropForeignKeyConstraint baseTableName=KEYCLOAK_ROLE, constraintName=FK_KJHO5LE2C0RAL09FL8CM9WFW9		\N	4.33.0	\N	\N	0528649527
map-remove-ri	keycloak	META-INF/jpa-changelog-12.0.0.xml	2025-10-15 11:44:15.402518	87	EXECUTED	9:a9ba7d47f065f041b7da856a81762021	dropForeignKeyConstraint baseTableName=REALM_DEFAULT_GROUPS, constraintName=FK_DEF_GROUPS_GROUP; dropForeignKeyConstraint baseTableName=REALM_DEFAULT_ROLES, constraintName=FK_H4WPD7W4HSOOLNI3H0SW7BTJE; dropForeignKeyConstraint baseTableName=CLIENT...		\N	4.33.0	\N	\N	0528649527
12.1.0-add-realm-localization-table	keycloak	META-INF/jpa-changelog-12.0.0.xml	2025-10-15 11:44:15.40608	88	EXECUTED	9:fffabce2bc01e1a8f5110d5278500065	createTable tableName=REALM_LOCALIZATIONS; addPrimaryKey tableName=REALM_LOCALIZATIONS		\N	4.33.0	\N	\N	0528649527
default-roles	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-10-15 11:44:15.408043	89	EXECUTED	9:fa8a5b5445e3857f4b010bafb5009957	addColumn tableName=REALM; customChange		\N	4.33.0	\N	\N	0528649527
default-roles-cleanup	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-10-15 11:44:15.410987	90	EXECUTED	9:67ac3241df9a8582d591c5ed87125f39	dropTable tableName=REALM_DEFAULT_ROLES; dropTable tableName=CLIENT_DEFAULT_ROLES		\N	4.33.0	\N	\N	0528649527
13.0.0-KEYCLOAK-16844	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-10-15 11:44:15.423715	91	EXECUTED	9:ad1194d66c937e3ffc82386c050ba089	createIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
map-remove-ri-13.0.0	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-10-15 11:44:15.427443	92	EXECUTED	9:d9be619d94af5a2f5d07b9f003543b91	dropForeignKeyConstraint baseTableName=DEFAULT_CLIENT_SCOPE, constraintName=FK_R_DEF_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SCOPE_CLIENT, constraintName=FK_C_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SC...		\N	4.33.0	\N	\N	0528649527
13.0.0-KEYCLOAK-17992-drop-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-10-15 11:44:15.427909	93	MARK_RAN	9:544d201116a0fcc5a5da0925fbbc3bde	dropPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CLSCOPE_CL, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CL_CLSCOPE, tableName=CLIENT_SCOPE_CLIENT		\N	4.33.0	\N	\N	0528649527
13.0.0-increase-column-size-federated	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-10-15 11:44:15.43092	94	EXECUTED	9:43c0c1055b6761b4b3e89de76d612ccf	modifyDataType columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; modifyDataType columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT		\N	4.33.0	\N	\N	0528649527
13.0.0-KEYCLOAK-17992-recreate-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-10-15 11:44:15.431466	95	MARK_RAN	9:8bd711fd0330f4fe980494ca43ab1139	addNotNullConstraint columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; addNotNullConstraint columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT; addPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; createIndex indexName=...		\N	4.33.0	\N	\N	0528649527
json-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-10-15 11:44:15.433411	96	EXECUTED	9:e07d2bc0970c348bb06fb63b1f82ddbf	addColumn tableName=REALM_ATTRIBUTE; update tableName=REALM_ATTRIBUTE; dropColumn columnName=VALUE, tableName=REALM_ATTRIBUTE; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=REALM_ATTRIBUTE		\N	4.33.0	\N	\N	0528649527
14.0.0-KEYCLOAK-11019	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-10-15 11:44:15.467276	97	EXECUTED	9:24fb8611e97f29989bea412aa38d12b7	createIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USER, tableName=OFFLINE_USER_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
14.0.0-KEYCLOAK-18286	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-10-15 11:44:15.468026	98	MARK_RAN	9:259f89014ce2506ee84740cbf7163aa7	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
14.0.0-KEYCLOAK-18286-revert	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-10-15 11:44:15.474534	99	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
14.0.0-KEYCLOAK-18286-supported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-10-15 11:44:15.488398	100	EXECUTED	9:60ca84a0f8c94ec8c3504a5a3bc88ee8	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
14.0.0-KEYCLOAK-18286-unsupported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-10-15 11:44:15.489052	101	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
KEYCLOAK-17267-add-index-to-user-attributes	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-10-15 11:44:15.50598	102	EXECUTED	9:0b305d8d1277f3a89a0a53a659ad274c	createIndex indexName=IDX_USER_ATTRIBUTE_NAME, tableName=USER_ATTRIBUTE		\N	4.33.0	\N	\N	0528649527
KEYCLOAK-18146-add-saml-art-binding-identifier	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-10-15 11:44:15.50874	103	EXECUTED	9:2c374ad2cdfe20e2905a84c8fac48460	customChange		\N	4.33.0	\N	\N	0528649527
15.0.0-KEYCLOAK-18467	keycloak	META-INF/jpa-changelog-15.0.0.xml	2025-10-15 11:44:15.510812	104	EXECUTED	9:47a760639ac597360a8219f5b768b4de	addColumn tableName=REALM_LOCALIZATIONS; update tableName=REALM_LOCALIZATIONS; dropColumn columnName=TEXTS, tableName=REALM_LOCALIZATIONS; renameColumn newColumnName=TEXTS, oldColumnName=TEXTS_NEW, tableName=REALM_LOCALIZATIONS; addNotNullConstrai...		\N	4.33.0	\N	\N	0528649527
17.0.0-9562	keycloak	META-INF/jpa-changelog-17.0.0.xml	2025-10-15 11:44:15.524328	105	EXECUTED	9:a6272f0576727dd8cad2522335f5d99e	createIndex indexName=IDX_USER_SERVICE_ACCOUNT, tableName=USER_ENTITY		\N	4.33.0	\N	\N	0528649527
18.0.0-10625-IDX_ADMIN_EVENT_TIME	keycloak	META-INF/jpa-changelog-18.0.0.xml	2025-10-15 11:44:15.540339	106	EXECUTED	9:015479dbd691d9cc8669282f4828c41d	createIndex indexName=IDX_ADMIN_EVENT_TIME, tableName=ADMIN_EVENT_ENTITY		\N	4.33.0	\N	\N	0528649527
18.0.15-30992-index-consent	keycloak	META-INF/jpa-changelog-18.0.15.xml	2025-10-15 11:44:15.568887	107	EXECUTED	9:80071ede7a05604b1f4906f3bf3b00f0	createIndex indexName=IDX_USCONSENT_SCOPE_ID, tableName=USER_CONSENT_CLIENT_SCOPE		\N	4.33.0	\N	\N	0528649527
19.0.0-10135	keycloak	META-INF/jpa-changelog-19.0.0.xml	2025-10-15 11:44:15.574385	108	EXECUTED	9:9518e495fdd22f78ad6425cc30630221	customChange		\N	4.33.0	\N	\N	0528649527
20.0.0-12964-supported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-10-15 11:44:15.602636	109	EXECUTED	9:e5f243877199fd96bcc842f27a1656ac	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.33.0	\N	\N	0528649527
20.0.0-12964-supported-dbs-edb-migration	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-10-15 11:44:15.666018	110	EXECUTED	9:a6b18a8e38062df5793edbe064f4aecd	dropIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE; createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.33.0	\N	\N	0528649527
20.0.0-12964-unsupported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-10-15 11:44:15.67173	111	MARK_RAN	9:1a6fcaa85e20bdeae0a9ce49b41946a5	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.33.0	\N	\N	0528649527
client-attributes-string-accomodation-fixed-pre-drop-index	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-10-15 11:44:15.681637	112	EXECUTED	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
client-attributes-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-10-15 11:44:15.696834	113	EXECUTED	9:3f332e13e90739ed0c35b0b25b7822ca	addColumn tableName=CLIENT_ATTRIBUTES; update tableName=CLIENT_ATTRIBUTES; dropColumn columnName=VALUE, tableName=CLIENT_ATTRIBUTES; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
client-attributes-string-accomodation-fixed-post-create-index	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-10-15 11:44:15.700089	114	MARK_RAN	9:bd2bd0fc7768cf0845ac96a8786fa735	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
21.0.2-17277	keycloak	META-INF/jpa-changelog-21.0.2.xml	2025-10-15 11:44:15.704519	115	EXECUTED	9:7ee1f7a3fb8f5588f171fb9a6ab623c0	customChange		\N	4.33.0	\N	\N	0528649527
21.1.0-19404	keycloak	META-INF/jpa-changelog-21.1.0.xml	2025-10-15 11:44:15.72237	116	EXECUTED	9:3d7e830b52f33676b9d64f7f2b2ea634	modifyDataType columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=LOGIC, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=POLICY_ENFORCE_MODE, tableName=RESOURCE_SERVER		\N	4.33.0	\N	\N	0528649527
21.1.0-19404-2	keycloak	META-INF/jpa-changelog-21.1.0.xml	2025-10-15 11:44:15.724339	117	MARK_RAN	9:627d032e3ef2c06c0e1f73d2ae25c26c	addColumn tableName=RESOURCE_SERVER_POLICY; update tableName=RESOURCE_SERVER_POLICY; dropColumn columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; renameColumn newColumnName=DECISION_STRATEGY, oldColumnName=DECISION_STRATEGY_NEW, tabl...		\N	4.33.0	\N	\N	0528649527
22.0.0-17484-updated	keycloak	META-INF/jpa-changelog-22.0.0.xml	2025-10-15 11:44:15.727714	118	EXECUTED	9:90af0bfd30cafc17b9f4d6eccd92b8b3	customChange		\N	4.33.0	\N	\N	0528649527
23.0.0-12062	keycloak	META-INF/jpa-changelog-23.0.0.xml	2025-10-15 11:44:15.730483	120	EXECUTED	9:2168fbe728fec46ae9baf15bf80927b8	addColumn tableName=COMPONENT_CONFIG; update tableName=COMPONENT_CONFIG; dropColumn columnName=VALUE, tableName=COMPONENT_CONFIG; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=COMPONENT_CONFIG		\N	4.33.0	\N	\N	0528649527
23.0.0-17258	keycloak	META-INF/jpa-changelog-23.0.0.xml	2025-10-15 11:44:15.73158	121	EXECUTED	9:36506d679a83bbfda85a27ea1864dca8	addColumn tableName=EVENT_ENTITY		\N	4.33.0	\N	\N	0528649527
24.0.0-9758	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-10-15 11:44:15.822807	122	EXECUTED	9:502c557a5189f600f0f445a9b49ebbce	addColumn tableName=USER_ATTRIBUTE; addColumn tableName=FED_USER_ATTRIBUTE; createIndex indexName=USER_ATTR_LONG_VALUES, tableName=USER_ATTRIBUTE; createIndex indexName=FED_USER_ATTR_LONG_VALUES, tableName=FED_USER_ATTRIBUTE; createIndex indexName...		\N	4.33.0	\N	\N	0528649527
24.0.0-9758-2	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-10-15 11:44:15.825064	123	EXECUTED	9:bf0fdee10afdf597a987adbf291db7b2	customChange		\N	4.33.0	\N	\N	0528649527
24.0.0-26618-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-10-15 11:44:15.828396	124	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
24.0.0-26618-reindex	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-10-15 11:44:15.853634	125	EXECUTED	9:08707c0f0db1cef6b352db03a60edc7f	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
24.0.0-26618-edb-migration	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-10-15 11:44:15.883335	126	EXECUTED	9:2f684b29d414cd47efe3a3599f390741	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES; createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
24.0.2-27228	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-10-15 11:44:15.886118	127	EXECUTED	9:eaee11f6b8aa25d2cc6a84fb86fc6238	customChange		\N	4.33.0	\N	\N	0528649527
24.0.2-27967-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-10-15 11:44:15.886926	128	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
24.0.2-27967-reindex	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-10-15 11:44:15.887969	129	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.33.0	\N	\N	0528649527
25.0.0-28265-tables	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.890314	130	EXECUTED	9:deda2df035df23388af95bbd36c17cef	addColumn tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_CLIENT_SESSION		\N	4.33.0	\N	\N	0528649527
25.0.0-28265-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.91733	131	EXECUTED	9:3e96709818458ae49f3c679ae58d263a	createIndex indexName=IDX_OFFLINE_USS_BY_LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
25.0.0-28265-index-cleanup-uss-createdon	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.921776	132	EXECUTED	9:78ab4fc129ed5e8265dbcc3485fba92f	dropIndex indexName=IDX_OFFLINE_USS_CREATEDON, tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
25.0.0-28265-index-cleanup-uss-preload	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.925209	133	EXECUTED	9:de5f7c1f7e10994ed8b62e621d20eaab	dropIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
25.0.0-28265-index-cleanup-uss-by-usersess	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.929208	134	EXECUTED	9:6eee220d024e38e89c799417ec33667f	dropIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
25.0.0-28265-index-cleanup-css-preload	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.933937	135	EXECUTED	9:5411d2fb2891d3e8d63ddb55dfa3c0c9	dropIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION		\N	4.33.0	\N	\N	0528649527
25.0.0-28265-index-2-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.935004	136	MARK_RAN	9:b7ef76036d3126bb83c2423bf4d449d6	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
25.0.0-28265-index-2-not-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.960903	137	EXECUTED	9:23396cf51ab8bc1ae6f0cac7f9f6fcf7	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.33.0	\N	\N	0528649527
25.0.0-org	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.969919	138	EXECUTED	9:5c859965c2c9b9c72136c360649af157	createTable tableName=ORG; addUniqueConstraint constraintName=UK_ORG_NAME, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_GROUP, tableName=ORG; createTable tableName=ORG_DOMAIN		\N	4.33.0	\N	\N	0528649527
unique-consentuser	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.97521	139	EXECUTED	9:5857626a2ea8767e9a6c66bf3a2cb32f	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.33.0	\N	\N	0528649527
unique-consentuser-edb-migration	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.981086	140	MARK_RAN	9:5857626a2ea8767e9a6c66bf3a2cb32f	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.33.0	\N	\N	0528649527
unique-consentuser-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:15.98274	141	MARK_RAN	9:b79478aad5adaa1bc428e31563f55e8e	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.33.0	\N	\N	0528649527
25.0.0-28861-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-10-15 11:44:16.030102	142	EXECUTED	9:b9acb58ac958d9ada0fe12a5d4794ab1	createIndex indexName=IDX_PERM_TICKET_REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; createIndex indexName=IDX_PERM_TICKET_OWNER, tableName=RESOURCE_SERVER_PERM_TICKET		\N	4.33.0	\N	\N	0528649527
26.0.0-org-alias	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.034816	143	EXECUTED	9:6ef7d63e4412b3c2d66ed179159886a4	addColumn tableName=ORG; update tableName=ORG; addNotNullConstraint columnName=ALIAS, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_ALIAS, tableName=ORG		\N	4.33.0	\N	\N	0528649527
26.0.0-org-group	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.039252	144	EXECUTED	9:da8e8087d80ef2ace4f89d8c5b9ca223	addColumn tableName=KEYCLOAK_GROUP; update tableName=KEYCLOAK_GROUP; addNotNullConstraint columnName=TYPE, tableName=KEYCLOAK_GROUP; customChange		\N	4.33.0	\N	\N	0528649527
26.0.0-org-indexes	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.062699	145	EXECUTED	9:79b05dcd610a8c7f25ec05135eec0857	createIndex indexName=IDX_ORG_DOMAIN_ORG_ID, tableName=ORG_DOMAIN		\N	4.33.0	\N	\N	0528649527
26.0.0-org-group-membership	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.064756	146	EXECUTED	9:a6ace2ce583a421d89b01ba2a28dc2d4	addColumn tableName=USER_GROUP_MEMBERSHIP; update tableName=USER_GROUP_MEMBERSHIP; addNotNullConstraint columnName=MEMBERSHIP_TYPE, tableName=USER_GROUP_MEMBERSHIP		\N	4.33.0	\N	\N	0528649527
31296-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.06734	147	EXECUTED	9:64ef94489d42a358e8304b0e245f0ed4	createTable tableName=REVOKED_TOKEN; addPrimaryKey constraintName=CONSTRAINT_RT, tableName=REVOKED_TOKEN		\N	4.33.0	\N	\N	0528649527
31725-index-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.089262	148	EXECUTED	9:b994246ec2bf7c94da881e1d28782c7b	createIndex indexName=IDX_REV_TOKEN_ON_EXPIRE, tableName=REVOKED_TOKEN		\N	4.33.0	\N	\N	0528649527
26.0.0-idps-for-login	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.136177	149	EXECUTED	9:51f5fffadf986983d4bd59582c6c1604	addColumn tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_REALM_ORG, tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_FOR_LOGIN, tableName=IDENTITY_PROVIDER; customChange		\N	4.33.0	\N	\N	0528649527
26.0.0-32583-drop-redundant-index-on-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.141161	150	EXECUTED	9:24972d83bf27317a055d234187bb4af9	dropIndex indexName=IDX_US_SESS_ID_ON_CL_SESS, tableName=OFFLINE_CLIENT_SESSION		\N	4.33.0	\N	\N	0528649527
26.0.0.32582-remove-tables-user-session-user-session-note-and-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.151471	151	EXECUTED	9:febdc0f47f2ed241c59e60f58c3ceea5	dropTable tableName=CLIENT_SESSION_ROLE; dropTable tableName=CLIENT_SESSION_NOTE; dropTable tableName=CLIENT_SESSION_PROT_MAPPER; dropTable tableName=CLIENT_SESSION_AUTH_STATUS; dropTable tableName=CLIENT_USER_SESSION_NOTE; dropTable tableName=CLI...		\N	4.33.0	\N	\N	0528649527
26.0.0-33201-org-redirect-url	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-10-15 11:44:16.153334	152	EXECUTED	9:4d0e22b0ac68ebe9794fa9cb752ea660	addColumn tableName=ORG		\N	4.33.0	\N	\N	0528649527
29399-jdbc-ping-default	keycloak	META-INF/jpa-changelog-26.1.0.xml	2025-10-15 11:44:16.158255	153	EXECUTED	9:007dbe99d7203fca403b89d4edfdf21e	createTable tableName=JGROUPS_PING; addPrimaryKey constraintName=CONSTRAINT_JGROUPS_PING, tableName=JGROUPS_PING		\N	4.33.0	\N	\N	0528649527
26.1.0-34013	keycloak	META-INF/jpa-changelog-26.1.0.xml	2025-10-15 11:44:16.16101	154	EXECUTED	9:e6b686a15759aef99a6d758a5c4c6a26	addColumn tableName=ADMIN_EVENT_ENTITY		\N	4.33.0	\N	\N	0528649527
26.1.0-34380	keycloak	META-INF/jpa-changelog-26.1.0.xml	2025-10-15 11:44:16.162945	155	EXECUTED	9:ac8b9edb7c2b6c17a1c7a11fcf5ccf01	dropTable tableName=USERNAME_LOGIN_FAILURE		\N	4.33.0	\N	\N	0528649527
26.2.0-36750	keycloak	META-INF/jpa-changelog-26.2.0.xml	2025-10-15 11:44:16.169205	156	EXECUTED	9:b49ce951c22f7eb16480ff085640a33a	createTable tableName=SERVER_CONFIG		\N	4.33.0	\N	\N	0528649527
26.2.0-26106	keycloak	META-INF/jpa-changelog-26.2.0.xml	2025-10-15 11:44:16.170946	157	EXECUTED	9:b5877d5dab7d10ff3a9d209d7beb6680	addColumn tableName=CREDENTIAL		\N	4.33.0	\N	\N	0528649527
26.2.6-39866-duplicate	keycloak	META-INF/jpa-changelog-26.2.6.xml	2025-10-15 11:44:16.172679	158	EXECUTED	9:1dc67ccee24f30331db2cba4f372e40e	customChange		\N	4.33.0	\N	\N	0528649527
26.2.6-39866-uk	keycloak	META-INF/jpa-changelog-26.2.6.xml	2025-10-15 11:44:16.174391	159	EXECUTED	9:b70b76f47210cf0a5f4ef0e219eac7cd	addUniqueConstraint constraintName=UK_MIGRATION_VERSION, tableName=MIGRATION_MODEL		\N	4.33.0	\N	\N	0528649527
26.2.6-40088-duplicate	keycloak	META-INF/jpa-changelog-26.2.6.xml	2025-10-15 11:44:16.17623	160	EXECUTED	9:cc7e02ed69ab31979afb1982f9670e8f	customChange		\N	4.33.0	\N	\N	0528649527
26.2.6-40088-uk	keycloak	META-INF/jpa-changelog-26.2.6.xml	2025-10-15 11:44:16.17807	161	EXECUTED	9:5bb848128da7bc4595cc507383325241	addUniqueConstraint constraintName=UK_MIGRATION_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.33.0	\N	\N	0528649527
26.3.0-groups-description	keycloak	META-INF/jpa-changelog-26.3.0.xml	2025-10-15 11:44:16.180305	162	EXECUTED	9:e1a3c05574326fb5b246b73b9a4c4d49	addColumn tableName=KEYCLOAK_GROUP		\N	4.33.0	\N	\N	0528649527
26.4.0-40933-saml-encryption-attributes	keycloak	META-INF/jpa-changelog-26.4.0.xml	2025-10-15 11:44:16.181774	163	EXECUTED	9:7e9eaba362ca105efdda202303a4fe49	customChange		\N	4.33.0	\N	\N	0528649527
26.4.0-51321	keycloak	META-INF/jpa-changelog-26.4.0.xml	2025-10-15 11:44:16.201498	164	EXECUTED	9:34bab2bc56f75ffd7e347c580874e306	createIndex indexName=IDX_EVENT_ENTITY_USER_ID_TYPE, tableName=EVENT_ENTITY		\N	4.33.0	\N	\N	0528649527
40343-workflow-state-table	keycloak	META-INF/jpa-changelog-26.4.0.xml	2025-10-15 11:44:16.249923	165	EXECUTED	9:ed3ab4723ceed210e5b5e60ac4562106	createTable tableName=WORKFLOW_STATE; addPrimaryKey constraintName=PK_WORKFLOW_STATE, tableName=WORKFLOW_STATE; addUniqueConstraint constraintName=UQ_WORKFLOW_RESOURCE, tableName=WORKFLOW_STATE; createIndex indexName=IDX_WORKFLOW_STATE_STEP, table...		\N	4.33.0	\N	\N	0528649527
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
1000	f	\N	\N
\.


--
-- Data for Name: default_client_scope; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.default_client_scope (realm_id, scope_id, default_scope) FROM stdin;
2522a2b1-50e1-43f3-947e-15bab8e65d6a	ad44ff53-1040-4b35-9e8f-ede27da2a5cb	f
2522a2b1-50e1-43f3-947e-15bab8e65d6a	7f4254c5-f8c3-4aed-90d8-a624c4af8b52	t
2522a2b1-50e1-43f3-947e-15bab8e65d6a	52200320-e220-422d-b453-f98a8e250420	t
2522a2b1-50e1-43f3-947e-15bab8e65d6a	d0485413-da03-4c3a-91a5-4219e7551095	t
2522a2b1-50e1-43f3-947e-15bab8e65d6a	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0	t
2522a2b1-50e1-43f3-947e-15bab8e65d6a	d5b5a828-823a-42ee-a4e3-c3532c896426	f
2522a2b1-50e1-43f3-947e-15bab8e65d6a	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc	f
2522a2b1-50e1-43f3-947e-15bab8e65d6a	5cc32155-3949-4283-980d-bc97497c21e8	t
2522a2b1-50e1-43f3-947e-15bab8e65d6a	92bca07a-04d6-4cee-970b-9a36b484c33c	t
2522a2b1-50e1-43f3-947e-15bab8e65d6a	1ee89a9b-aa2c-4a44-896c-a942160b8976	f
2522a2b1-50e1-43f3-947e-15bab8e65d6a	bd40343b-10d5-4eaf-8fd0-6bc5122ff280	t
2522a2b1-50e1-43f3-947e-15bab8e65d6a	663d10f9-9ab7-463a-aade-b824010d8b28	t
2522a2b1-50e1-43f3-947e-15bab8e65d6a	bef193f9-e920-42e7-a3c9-221caa197ad2	f
\.


--
-- Data for Name: event_entity; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.event_entity (id, client_id, details_json, error, ip_address, realm_id, session_id, event_time, type, user_id, details_json_long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.fed_user_attribute (id, name, user_id, realm_id, storage_provider_id, value, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_consent; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.fed_user_consent (id, client_id, user_id, realm_id, storage_provider_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: fed_user_consent_cl_scope; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.fed_user_consent_cl_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: fed_user_credential; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.fed_user_credential (id, salt, type, created_date, user_id, realm_id, storage_provider_id, user_label, secret_data, credential_data, priority) FROM stdin;
\.


--
-- Data for Name: fed_user_group_membership; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.fed_user_group_membership (group_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_required_action; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.fed_user_required_action (required_action, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_role_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.fed_user_role_mapping (role_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: federated_identity; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.federated_identity (identity_provider, realm_id, federated_user_id, federated_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: federated_user; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.federated_user (id, storage_provider_id, realm_id) FROM stdin;
\.


--
-- Data for Name: group_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.group_attribute (id, name, value, group_id) FROM stdin;
\.


--
-- Data for Name: group_role_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.group_role_mapping (role_id, group_id) FROM stdin;
\.


--
-- Data for Name: identity_provider; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.identity_provider (internal_id, enabled, provider_alias, provider_id, store_token, authenticate_by_default, realm_id, add_token_role, trust_email, first_broker_login_flow_id, post_broker_login_flow_id, provider_display_name, link_only, organization_id, hide_on_login) FROM stdin;
\.


--
-- Data for Name: identity_provider_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.identity_provider_config (identity_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: identity_provider_mapper; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.identity_provider_mapper (id, name, idp_alias, idp_mapper_name, realm_id) FROM stdin;
\.


--
-- Data for Name: idp_mapper_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.idp_mapper_config (idp_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: jgroups_ping; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.jgroups_ping (address, name, cluster_name, ip, coord) FROM stdin;
\.


--
-- Data for Name: keycloak_group; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.keycloak_group (id, name, parent_group, realm_id, type, description) FROM stdin;
\.


--
-- Data for Name: keycloak_role; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.keycloak_role (id, client_realm_constraint, client_role, description, name, realm_id, client, realm) FROM stdin;
0794b2aa-e7c5-4511-b7e4-b70e017ee375	2522a2b1-50e1-43f3-947e-15bab8e65d6a	f	${role_default-roles}	default-roles-master	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N	\N
5e3e0557-02af-4ad5-9ad6-3499e53b1623	2522a2b1-50e1-43f3-947e-15bab8e65d6a	f	${role_create-realm}	create-realm	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N	\N
7226339e-3ab6-47c4-95ec-3737455f18b9	2522a2b1-50e1-43f3-947e-15bab8e65d6a	f	${role_admin}	admin	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N	\N
6568a4c5-5ab9-4e68-a5bb-45106d5a281c	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_create-client}	create-client	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
71ad2f48-f902-4f9d-9149-80a5ef7d5a8f	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_view-realm}	view-realm	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
69ef72dd-84a6-49c9-ba40-cc460863e8e3	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_view-users}	view-users	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
34cfd536-9213-4917-917c-751b4e343316	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_view-clients}	view-clients	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
914e9dbd-a8ab-4ec3-bb2d-cf3d2d2ab404	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_view-events}	view-events	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
f627261d-4dfd-45b2-b93c-73bbfbb725de	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_view-identity-providers}	view-identity-providers	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
2859f9ff-f766-4e0d-8ab4-1e8117c10d60	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_view-authorization}	view-authorization	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
53f729d3-094b-436a-a549-376a0febb00c	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_manage-realm}	manage-realm	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
1ea03edf-1ef8-4239-b3b9-6987ef11c040	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_manage-users}	manage-users	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
9c9cc5df-7638-43e0-91d7-3684b7a2c098	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_manage-clients}	manage-clients	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
5fd7b8a9-6867-47b3-a107-8f3d66634966	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_manage-events}	manage-events	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
89283bce-e881-443a-b93c-56f35dd79f28	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_manage-identity-providers}	manage-identity-providers	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
2b5c705e-b163-43ab-b604-4444a0944c23	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_manage-authorization}	manage-authorization	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
f26ca79c-71fa-4e59-bb48-2fc1bf425c0f	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_query-users}	query-users	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
ef15f1cb-06a9-4ea0-8ccd-b43b1b2b8569	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_query-clients}	query-clients	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
eaf15d14-fb80-4854-aaa5-ee76eb150396	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_query-realms}	query-realms	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
6ad38393-1e9f-4dec-860c-bfebffcc5a25	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_query-groups}	query-groups	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
3e8300fa-4e66-4a29-9717-c4cfd540c074	822c0def-a413-405f-ada7-901a19f0e14a	t	${role_view-profile}	view-profile	2522a2b1-50e1-43f3-947e-15bab8e65d6a	822c0def-a413-405f-ada7-901a19f0e14a	\N
dff8d26f-16bb-4c9d-9295-8800a2a6adf4	822c0def-a413-405f-ada7-901a19f0e14a	t	${role_manage-account}	manage-account	2522a2b1-50e1-43f3-947e-15bab8e65d6a	822c0def-a413-405f-ada7-901a19f0e14a	\N
d30a45df-bf49-4775-aab8-ca17a3e10efd	822c0def-a413-405f-ada7-901a19f0e14a	t	${role_manage-account-links}	manage-account-links	2522a2b1-50e1-43f3-947e-15bab8e65d6a	822c0def-a413-405f-ada7-901a19f0e14a	\N
6c25f936-89fa-4c4d-9ab5-8ba81ed011b2	822c0def-a413-405f-ada7-901a19f0e14a	t	${role_view-applications}	view-applications	2522a2b1-50e1-43f3-947e-15bab8e65d6a	822c0def-a413-405f-ada7-901a19f0e14a	\N
036578e3-eb6f-4856-bb02-28584bebb14d	822c0def-a413-405f-ada7-901a19f0e14a	t	${role_view-consent}	view-consent	2522a2b1-50e1-43f3-947e-15bab8e65d6a	822c0def-a413-405f-ada7-901a19f0e14a	\N
1aa18df7-e256-4f7c-9dca-95a28ed6b805	822c0def-a413-405f-ada7-901a19f0e14a	t	${role_manage-consent}	manage-consent	2522a2b1-50e1-43f3-947e-15bab8e65d6a	822c0def-a413-405f-ada7-901a19f0e14a	\N
cbcb44e1-28e9-4579-816f-0b5c0f4f5bcf	822c0def-a413-405f-ada7-901a19f0e14a	t	${role_view-groups}	view-groups	2522a2b1-50e1-43f3-947e-15bab8e65d6a	822c0def-a413-405f-ada7-901a19f0e14a	\N
cadfc598-e161-443d-a3a9-c4ec1b458d82	822c0def-a413-405f-ada7-901a19f0e14a	t	${role_delete-account}	delete-account	2522a2b1-50e1-43f3-947e-15bab8e65d6a	822c0def-a413-405f-ada7-901a19f0e14a	\N
11b4a421-445a-4db0-8f03-78080adc8977	ce701a82-9534-4f28-8501-8af5485fdbef	t	${role_read-token}	read-token	2522a2b1-50e1-43f3-947e-15bab8e65d6a	ce701a82-9534-4f28-8501-8af5485fdbef	\N
ff628bb2-ece9-4e45-8b52-550b073b051b	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	t	${role_impersonation}	impersonation	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	\N
1db8d9c4-753a-4f46-a35d-39b0efe93bcb	2522a2b1-50e1-43f3-947e-15bab8e65d6a	f	${role_offline-access}	offline_access	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N	\N
e79eb703-2a61-426b-a40b-8e890e048f58	2522a2b1-50e1-43f3-947e-15bab8e65d6a	f	${role_uma_authorization}	uma_authorization	2522a2b1-50e1-43f3-947e-15bab8e65d6a	\N	\N
\.


--
-- Data for Name: migration_model; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.migration_model (id, version, update_time) FROM stdin;
jmbh6	26.4.0	1760528658
\.


--
-- Data for Name: offline_client_session; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.offline_client_session (user_session_id, client_id, offline_flag, "timestamp", data, client_storage_provider, external_client_id, version) FROM stdin;
\.


--
-- Data for Name: offline_user_session; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.offline_user_session (user_session_id, user_id, realm_id, created_on, offline_flag, data, last_session_refresh, broker_session_id, version) FROM stdin;
\.


--
-- Data for Name: org; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.org (id, enabled, realm_id, group_id, name, description, alias, redirect_url) FROM stdin;
\.


--
-- Data for Name: org_domain; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.org_domain (id, name, verified, org_id) FROM stdin;
\.


--
-- Data for Name: policy_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.policy_config (policy_id, name, value) FROM stdin;
\.


--
-- Data for Name: protocol_mapper; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.protocol_mapper (id, name, protocol, protocol_mapper_name, client_id, client_scope_id) FROM stdin;
2b3299e7-5bf1-40d9-9b3f-cac92ed5f7dc	audience resolve	openid-connect	oidc-audience-resolve-mapper	d3fd2493-8a37-4cf7-9c70-b05841741543	\N
c03d27fd-eee9-4c8a-b93f-cc6178da3402	locale	openid-connect	oidc-usermodel-attribute-mapper	d1318f74-13c0-4a48-a30a-08b679c21f15	\N
e50f9583-d0e6-4541-8587-d462cf331abc	role list	saml	saml-role-list-mapper	\N	7f4254c5-f8c3-4aed-90d8-a624c4af8b52
69ba71f1-71f5-41b5-b269-c8951800d14a	organization	saml	saml-organization-membership-mapper	\N	52200320-e220-422d-b453-f98a8e250420
e84286ce-e8a3-409d-b03c-26ed384691b4	full name	openid-connect	oidc-full-name-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
d668d230-ea66-49ba-b667-381a5762079b	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
ab979d59-d76c-4a2f-ab0b-e2a8130a3557	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
ae134afb-dd87-4357-9210-8d1f8f19dbb3	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
2e227684-087f-4ed6-9207-949700ac5046	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
59244883-45af-47eb-81c0-3a52aade2bdb	username	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
8636dd28-b3b5-47a6-8d3a-2960f4526ba4	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
f4f142d7-8a57-4ce1-86d1-f89689d0ae3a	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
75e0c4fa-bf5d-4cdc-a03e-8c38037b9321	website	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
9b641075-eeca-4902-b797-840b9d287323	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
dccd797d-9a29-417d-a69f-bc4398761797	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
3cade3c2-adb9-45d3-8df4-9d1e98926aed	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
f001c0c3-c967-4485-ab8b-b5251c4e1512	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
53dd9fdd-9dab-4c9a-a3ec-a2cceb7cc48d	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	d0485413-da03-4c3a-91a5-4219e7551095
bbceb6fc-0630-4e84-884b-23ab7aca37fa	email	openid-connect	oidc-usermodel-attribute-mapper	\N	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0
80dc3597-aa17-4b07-8baf-7dab0296fff3	email verified	openid-connect	oidc-usermodel-property-mapper	\N	73e6d0c3-4060-4b4b-83bb-4a42c8d77aa0
3f2e0962-7f2d-4555-9955-a011d8b77516	address	openid-connect	oidc-address-mapper	\N	d5b5a828-823a-42ee-a4e3-c3532c896426
3b53c56c-c28a-4724-8ad5-3a468dd56c76	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc
5b3f06a8-b50c-47d3-983b-341edb196f91	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	f1c5a9f5-ea15-4502-b737-e5e9d5c894cc
9c9365b9-160b-483f-8c0c-da61f6013a1f	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	5cc32155-3949-4283-980d-bc97497c21e8
c1f1568c-cfe3-417a-bd9f-af331e0ae766	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	5cc32155-3949-4283-980d-bc97497c21e8
3c53d4c3-9ccc-4ce5-ac29-3c232c57a44d	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	5cc32155-3949-4283-980d-bc97497c21e8
39b0b1e1-7e53-48ee-95f4-bfebae851a40	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	92bca07a-04d6-4cee-970b-9a36b484c33c
baaec7f0-2f3b-4269-b68a-73db9813f858	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee89a9b-aa2c-4a44-896c-a942160b8976
40baa2cd-6f2b-4fc4-a5f0-68d69578681d	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	1ee89a9b-aa2c-4a44-896c-a942160b8976
0bbe162f-262e-42d9-9a6f-3caef94a81a6	acr loa level	openid-connect	oidc-acr-mapper	\N	bd40343b-10d5-4eaf-8fd0-6bc5122ff280
a6a9780b-157c-4f40-95b6-8d6b92b9dc58	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	663d10f9-9ab7-463a-aade-b824010d8b28
75b1344f-d032-4daf-b90d-d6b618e0f1c9	sub	openid-connect	oidc-sub-mapper	\N	663d10f9-9ab7-463a-aade-b824010d8b28
7ec7beab-0ca9-4aea-8cee-17e883d5379d	Client ID	openid-connect	oidc-usersessionmodel-note-mapper	\N	2dfed20c-b325-4d0c-a0db-834457fb7bd5
35395221-04cc-4166-8ad1-c0a0159c238b	Client Host	openid-connect	oidc-usersessionmodel-note-mapper	\N	2dfed20c-b325-4d0c-a0db-834457fb7bd5
20f3939c-29df-4df3-a259-0825a5f8de45	Client IP Address	openid-connect	oidc-usersessionmodel-note-mapper	\N	2dfed20c-b325-4d0c-a0db-834457fb7bd5
687b9d1b-fbb6-4d9b-abc3-b9ef616cf097	organization	openid-connect	oidc-organization-membership-mapper	\N	bef193f9-e920-42e7-a3c9-221caa197ad2
5211cbcc-768e-4d83-83c6-611489fe2d45	username	openid-connect	oidc-usermodel-attribute-mapper	99c140d7-b340-4425-9fde-800f2f552715	\N
\.


--
-- Data for Name: protocol_mapper_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.protocol_mapper_config (protocol_mapper_id, value, name) FROM stdin;
c03d27fd-eee9-4c8a-b93f-cc6178da3402	true	introspection.token.claim
c03d27fd-eee9-4c8a-b93f-cc6178da3402	true	userinfo.token.claim
c03d27fd-eee9-4c8a-b93f-cc6178da3402	locale	user.attribute
c03d27fd-eee9-4c8a-b93f-cc6178da3402	true	id.token.claim
c03d27fd-eee9-4c8a-b93f-cc6178da3402	true	access.token.claim
c03d27fd-eee9-4c8a-b93f-cc6178da3402	locale	claim.name
c03d27fd-eee9-4c8a-b93f-cc6178da3402	String	jsonType.label
e50f9583-d0e6-4541-8587-d462cf331abc	false	single
e50f9583-d0e6-4541-8587-d462cf331abc	Basic	attribute.nameformat
e50f9583-d0e6-4541-8587-d462cf331abc	Role	attribute.name
2e227684-087f-4ed6-9207-949700ac5046	true	introspection.token.claim
2e227684-087f-4ed6-9207-949700ac5046	true	userinfo.token.claim
2e227684-087f-4ed6-9207-949700ac5046	nickname	user.attribute
2e227684-087f-4ed6-9207-949700ac5046	true	id.token.claim
2e227684-087f-4ed6-9207-949700ac5046	true	access.token.claim
2e227684-087f-4ed6-9207-949700ac5046	nickname	claim.name
2e227684-087f-4ed6-9207-949700ac5046	String	jsonType.label
3cade3c2-adb9-45d3-8df4-9d1e98926aed	true	introspection.token.claim
3cade3c2-adb9-45d3-8df4-9d1e98926aed	true	userinfo.token.claim
3cade3c2-adb9-45d3-8df4-9d1e98926aed	zoneinfo	user.attribute
3cade3c2-adb9-45d3-8df4-9d1e98926aed	true	id.token.claim
3cade3c2-adb9-45d3-8df4-9d1e98926aed	true	access.token.claim
3cade3c2-adb9-45d3-8df4-9d1e98926aed	zoneinfo	claim.name
3cade3c2-adb9-45d3-8df4-9d1e98926aed	String	jsonType.label
53dd9fdd-9dab-4c9a-a3ec-a2cceb7cc48d	true	introspection.token.claim
53dd9fdd-9dab-4c9a-a3ec-a2cceb7cc48d	true	userinfo.token.claim
53dd9fdd-9dab-4c9a-a3ec-a2cceb7cc48d	updatedAt	user.attribute
53dd9fdd-9dab-4c9a-a3ec-a2cceb7cc48d	true	id.token.claim
53dd9fdd-9dab-4c9a-a3ec-a2cceb7cc48d	true	access.token.claim
53dd9fdd-9dab-4c9a-a3ec-a2cceb7cc48d	updated_at	claim.name
53dd9fdd-9dab-4c9a-a3ec-a2cceb7cc48d	long	jsonType.label
59244883-45af-47eb-81c0-3a52aade2bdb	true	introspection.token.claim
59244883-45af-47eb-81c0-3a52aade2bdb	true	userinfo.token.claim
59244883-45af-47eb-81c0-3a52aade2bdb	username	user.attribute
59244883-45af-47eb-81c0-3a52aade2bdb	true	id.token.claim
59244883-45af-47eb-81c0-3a52aade2bdb	true	access.token.claim
59244883-45af-47eb-81c0-3a52aade2bdb	preferred_username	claim.name
59244883-45af-47eb-81c0-3a52aade2bdb	String	jsonType.label
75e0c4fa-bf5d-4cdc-a03e-8c38037b9321	true	introspection.token.claim
75e0c4fa-bf5d-4cdc-a03e-8c38037b9321	true	userinfo.token.claim
75e0c4fa-bf5d-4cdc-a03e-8c38037b9321	website	user.attribute
75e0c4fa-bf5d-4cdc-a03e-8c38037b9321	true	id.token.claim
75e0c4fa-bf5d-4cdc-a03e-8c38037b9321	true	access.token.claim
75e0c4fa-bf5d-4cdc-a03e-8c38037b9321	website	claim.name
75e0c4fa-bf5d-4cdc-a03e-8c38037b9321	String	jsonType.label
8636dd28-b3b5-47a6-8d3a-2960f4526ba4	true	introspection.token.claim
8636dd28-b3b5-47a6-8d3a-2960f4526ba4	true	userinfo.token.claim
8636dd28-b3b5-47a6-8d3a-2960f4526ba4	profile	user.attribute
8636dd28-b3b5-47a6-8d3a-2960f4526ba4	true	id.token.claim
8636dd28-b3b5-47a6-8d3a-2960f4526ba4	true	access.token.claim
8636dd28-b3b5-47a6-8d3a-2960f4526ba4	profile	claim.name
8636dd28-b3b5-47a6-8d3a-2960f4526ba4	String	jsonType.label
9b641075-eeca-4902-b797-840b9d287323	true	introspection.token.claim
9b641075-eeca-4902-b797-840b9d287323	true	userinfo.token.claim
9b641075-eeca-4902-b797-840b9d287323	gender	user.attribute
9b641075-eeca-4902-b797-840b9d287323	true	id.token.claim
9b641075-eeca-4902-b797-840b9d287323	true	access.token.claim
9b641075-eeca-4902-b797-840b9d287323	gender	claim.name
9b641075-eeca-4902-b797-840b9d287323	String	jsonType.label
ab979d59-d76c-4a2f-ab0b-e2a8130a3557	true	introspection.token.claim
ab979d59-d76c-4a2f-ab0b-e2a8130a3557	true	userinfo.token.claim
ab979d59-d76c-4a2f-ab0b-e2a8130a3557	firstName	user.attribute
ab979d59-d76c-4a2f-ab0b-e2a8130a3557	true	id.token.claim
ab979d59-d76c-4a2f-ab0b-e2a8130a3557	true	access.token.claim
ab979d59-d76c-4a2f-ab0b-e2a8130a3557	given_name	claim.name
ab979d59-d76c-4a2f-ab0b-e2a8130a3557	String	jsonType.label
ae134afb-dd87-4357-9210-8d1f8f19dbb3	true	introspection.token.claim
ae134afb-dd87-4357-9210-8d1f8f19dbb3	true	userinfo.token.claim
ae134afb-dd87-4357-9210-8d1f8f19dbb3	middleName	user.attribute
ae134afb-dd87-4357-9210-8d1f8f19dbb3	true	id.token.claim
ae134afb-dd87-4357-9210-8d1f8f19dbb3	true	access.token.claim
ae134afb-dd87-4357-9210-8d1f8f19dbb3	middle_name	claim.name
ae134afb-dd87-4357-9210-8d1f8f19dbb3	String	jsonType.label
d668d230-ea66-49ba-b667-381a5762079b	true	introspection.token.claim
d668d230-ea66-49ba-b667-381a5762079b	true	userinfo.token.claim
d668d230-ea66-49ba-b667-381a5762079b	lastName	user.attribute
d668d230-ea66-49ba-b667-381a5762079b	true	id.token.claim
d668d230-ea66-49ba-b667-381a5762079b	true	access.token.claim
d668d230-ea66-49ba-b667-381a5762079b	family_name	claim.name
d668d230-ea66-49ba-b667-381a5762079b	String	jsonType.label
dccd797d-9a29-417d-a69f-bc4398761797	true	introspection.token.claim
dccd797d-9a29-417d-a69f-bc4398761797	true	userinfo.token.claim
dccd797d-9a29-417d-a69f-bc4398761797	birthdate	user.attribute
dccd797d-9a29-417d-a69f-bc4398761797	true	id.token.claim
dccd797d-9a29-417d-a69f-bc4398761797	true	access.token.claim
dccd797d-9a29-417d-a69f-bc4398761797	birthdate	claim.name
dccd797d-9a29-417d-a69f-bc4398761797	String	jsonType.label
e84286ce-e8a3-409d-b03c-26ed384691b4	true	introspection.token.claim
e84286ce-e8a3-409d-b03c-26ed384691b4	true	userinfo.token.claim
e84286ce-e8a3-409d-b03c-26ed384691b4	true	id.token.claim
e84286ce-e8a3-409d-b03c-26ed384691b4	true	access.token.claim
f001c0c3-c967-4485-ab8b-b5251c4e1512	true	introspection.token.claim
f001c0c3-c967-4485-ab8b-b5251c4e1512	true	userinfo.token.claim
f001c0c3-c967-4485-ab8b-b5251c4e1512	locale	user.attribute
f001c0c3-c967-4485-ab8b-b5251c4e1512	true	id.token.claim
f001c0c3-c967-4485-ab8b-b5251c4e1512	true	access.token.claim
f001c0c3-c967-4485-ab8b-b5251c4e1512	locale	claim.name
f001c0c3-c967-4485-ab8b-b5251c4e1512	String	jsonType.label
f4f142d7-8a57-4ce1-86d1-f89689d0ae3a	true	introspection.token.claim
f4f142d7-8a57-4ce1-86d1-f89689d0ae3a	true	userinfo.token.claim
f4f142d7-8a57-4ce1-86d1-f89689d0ae3a	picture	user.attribute
f4f142d7-8a57-4ce1-86d1-f89689d0ae3a	true	id.token.claim
f4f142d7-8a57-4ce1-86d1-f89689d0ae3a	true	access.token.claim
f4f142d7-8a57-4ce1-86d1-f89689d0ae3a	picture	claim.name
f4f142d7-8a57-4ce1-86d1-f89689d0ae3a	String	jsonType.label
80dc3597-aa17-4b07-8baf-7dab0296fff3	true	introspection.token.claim
80dc3597-aa17-4b07-8baf-7dab0296fff3	true	userinfo.token.claim
80dc3597-aa17-4b07-8baf-7dab0296fff3	emailVerified	user.attribute
80dc3597-aa17-4b07-8baf-7dab0296fff3	true	id.token.claim
80dc3597-aa17-4b07-8baf-7dab0296fff3	true	access.token.claim
80dc3597-aa17-4b07-8baf-7dab0296fff3	email_verified	claim.name
80dc3597-aa17-4b07-8baf-7dab0296fff3	boolean	jsonType.label
bbceb6fc-0630-4e84-884b-23ab7aca37fa	true	introspection.token.claim
bbceb6fc-0630-4e84-884b-23ab7aca37fa	true	userinfo.token.claim
bbceb6fc-0630-4e84-884b-23ab7aca37fa	email	user.attribute
bbceb6fc-0630-4e84-884b-23ab7aca37fa	true	id.token.claim
bbceb6fc-0630-4e84-884b-23ab7aca37fa	true	access.token.claim
bbceb6fc-0630-4e84-884b-23ab7aca37fa	email	claim.name
bbceb6fc-0630-4e84-884b-23ab7aca37fa	String	jsonType.label
3f2e0962-7f2d-4555-9955-a011d8b77516	formatted	user.attribute.formatted
3f2e0962-7f2d-4555-9955-a011d8b77516	country	user.attribute.country
3f2e0962-7f2d-4555-9955-a011d8b77516	true	introspection.token.claim
3f2e0962-7f2d-4555-9955-a011d8b77516	postal_code	user.attribute.postal_code
3f2e0962-7f2d-4555-9955-a011d8b77516	true	userinfo.token.claim
3f2e0962-7f2d-4555-9955-a011d8b77516	street	user.attribute.street
3f2e0962-7f2d-4555-9955-a011d8b77516	true	id.token.claim
3f2e0962-7f2d-4555-9955-a011d8b77516	region	user.attribute.region
3f2e0962-7f2d-4555-9955-a011d8b77516	true	access.token.claim
3f2e0962-7f2d-4555-9955-a011d8b77516	locality	user.attribute.locality
3b53c56c-c28a-4724-8ad5-3a468dd56c76	true	introspection.token.claim
3b53c56c-c28a-4724-8ad5-3a468dd56c76	true	userinfo.token.claim
3b53c56c-c28a-4724-8ad5-3a468dd56c76	phoneNumber	user.attribute
3b53c56c-c28a-4724-8ad5-3a468dd56c76	true	id.token.claim
3b53c56c-c28a-4724-8ad5-3a468dd56c76	true	access.token.claim
3b53c56c-c28a-4724-8ad5-3a468dd56c76	phone_number	claim.name
3b53c56c-c28a-4724-8ad5-3a468dd56c76	String	jsonType.label
5b3f06a8-b50c-47d3-983b-341edb196f91	true	introspection.token.claim
5b3f06a8-b50c-47d3-983b-341edb196f91	true	userinfo.token.claim
5b3f06a8-b50c-47d3-983b-341edb196f91	phoneNumberVerified	user.attribute
5b3f06a8-b50c-47d3-983b-341edb196f91	true	id.token.claim
5b3f06a8-b50c-47d3-983b-341edb196f91	true	access.token.claim
5b3f06a8-b50c-47d3-983b-341edb196f91	phone_number_verified	claim.name
5b3f06a8-b50c-47d3-983b-341edb196f91	boolean	jsonType.label
3c53d4c3-9ccc-4ce5-ac29-3c232c57a44d	true	introspection.token.claim
3c53d4c3-9ccc-4ce5-ac29-3c232c57a44d	true	access.token.claim
9c9365b9-160b-483f-8c0c-da61f6013a1f	true	introspection.token.claim
9c9365b9-160b-483f-8c0c-da61f6013a1f	true	multivalued
9c9365b9-160b-483f-8c0c-da61f6013a1f	foo	user.attribute
9c9365b9-160b-483f-8c0c-da61f6013a1f	true	access.token.claim
9c9365b9-160b-483f-8c0c-da61f6013a1f	realm_access.roles	claim.name
9c9365b9-160b-483f-8c0c-da61f6013a1f	String	jsonType.label
c1f1568c-cfe3-417a-bd9f-af331e0ae766	true	introspection.token.claim
c1f1568c-cfe3-417a-bd9f-af331e0ae766	true	multivalued
c1f1568c-cfe3-417a-bd9f-af331e0ae766	foo	user.attribute
c1f1568c-cfe3-417a-bd9f-af331e0ae766	true	access.token.claim
c1f1568c-cfe3-417a-bd9f-af331e0ae766	resource_access.${client_id}.roles	claim.name
c1f1568c-cfe3-417a-bd9f-af331e0ae766	String	jsonType.label
39b0b1e1-7e53-48ee-95f4-bfebae851a40	true	introspection.token.claim
39b0b1e1-7e53-48ee-95f4-bfebae851a40	true	access.token.claim
40baa2cd-6f2b-4fc4-a5f0-68d69578681d	true	introspection.token.claim
40baa2cd-6f2b-4fc4-a5f0-68d69578681d	true	multivalued
40baa2cd-6f2b-4fc4-a5f0-68d69578681d	foo	user.attribute
40baa2cd-6f2b-4fc4-a5f0-68d69578681d	true	id.token.claim
40baa2cd-6f2b-4fc4-a5f0-68d69578681d	true	access.token.claim
40baa2cd-6f2b-4fc4-a5f0-68d69578681d	groups	claim.name
40baa2cd-6f2b-4fc4-a5f0-68d69578681d	String	jsonType.label
baaec7f0-2f3b-4269-b68a-73db9813f858	true	introspection.token.claim
baaec7f0-2f3b-4269-b68a-73db9813f858	true	userinfo.token.claim
baaec7f0-2f3b-4269-b68a-73db9813f858	username	user.attribute
baaec7f0-2f3b-4269-b68a-73db9813f858	true	id.token.claim
baaec7f0-2f3b-4269-b68a-73db9813f858	true	access.token.claim
baaec7f0-2f3b-4269-b68a-73db9813f858	upn	claim.name
baaec7f0-2f3b-4269-b68a-73db9813f858	String	jsonType.label
0bbe162f-262e-42d9-9a6f-3caef94a81a6	true	introspection.token.claim
0bbe162f-262e-42d9-9a6f-3caef94a81a6	true	id.token.claim
0bbe162f-262e-42d9-9a6f-3caef94a81a6	true	access.token.claim
75b1344f-d032-4daf-b90d-d6b618e0f1c9	true	introspection.token.claim
75b1344f-d032-4daf-b90d-d6b618e0f1c9	true	access.token.claim
a6a9780b-157c-4f40-95b6-8d6b92b9dc58	AUTH_TIME	user.session.note
a6a9780b-157c-4f40-95b6-8d6b92b9dc58	true	introspection.token.claim
a6a9780b-157c-4f40-95b6-8d6b92b9dc58	true	id.token.claim
a6a9780b-157c-4f40-95b6-8d6b92b9dc58	true	access.token.claim
a6a9780b-157c-4f40-95b6-8d6b92b9dc58	auth_time	claim.name
a6a9780b-157c-4f40-95b6-8d6b92b9dc58	long	jsonType.label
20f3939c-29df-4df3-a259-0825a5f8de45	clientAddress	user.session.note
20f3939c-29df-4df3-a259-0825a5f8de45	true	introspection.token.claim
20f3939c-29df-4df3-a259-0825a5f8de45	true	id.token.claim
20f3939c-29df-4df3-a259-0825a5f8de45	true	access.token.claim
20f3939c-29df-4df3-a259-0825a5f8de45	clientAddress	claim.name
20f3939c-29df-4df3-a259-0825a5f8de45	String	jsonType.label
35395221-04cc-4166-8ad1-c0a0159c238b	clientHost	user.session.note
35395221-04cc-4166-8ad1-c0a0159c238b	true	introspection.token.claim
35395221-04cc-4166-8ad1-c0a0159c238b	true	id.token.claim
35395221-04cc-4166-8ad1-c0a0159c238b	true	access.token.claim
35395221-04cc-4166-8ad1-c0a0159c238b	clientHost	claim.name
35395221-04cc-4166-8ad1-c0a0159c238b	String	jsonType.label
7ec7beab-0ca9-4aea-8cee-17e883d5379d	client_id	user.session.note
7ec7beab-0ca9-4aea-8cee-17e883d5379d	true	introspection.token.claim
7ec7beab-0ca9-4aea-8cee-17e883d5379d	true	id.token.claim
7ec7beab-0ca9-4aea-8cee-17e883d5379d	true	access.token.claim
7ec7beab-0ca9-4aea-8cee-17e883d5379d	client_id	claim.name
7ec7beab-0ca9-4aea-8cee-17e883d5379d	String	jsonType.label
687b9d1b-fbb6-4d9b-abc3-b9ef616cf097	true	introspection.token.claim
687b9d1b-fbb6-4d9b-abc3-b9ef616cf097	true	multivalued
687b9d1b-fbb6-4d9b-abc3-b9ef616cf097	true	id.token.claim
687b9d1b-fbb6-4d9b-abc3-b9ef616cf097	true	access.token.claim
687b9d1b-fbb6-4d9b-abc3-b9ef616cf097	organization	claim.name
687b9d1b-fbb6-4d9b-abc3-b9ef616cf097	String	jsonType.label
5211cbcc-768e-4d83-83c6-611489fe2d45	true	introspection.token.claim
5211cbcc-768e-4d83-83c6-611489fe2d45	true	userinfo.token.claim
5211cbcc-768e-4d83-83c6-611489fe2d45	username	user.attribute
5211cbcc-768e-4d83-83c6-611489fe2d45	true	id.token.claim
5211cbcc-768e-4d83-83c6-611489fe2d45	false	lightweight.claim
5211cbcc-768e-4d83-83c6-611489fe2d45	true	access.token.claim
5211cbcc-768e-4d83-83c6-611489fe2d45	preferred_username	claim.name
5211cbcc-768e-4d83-83c6-611489fe2d45	String	jsonType.label
\.


--
-- Data for Name: realm; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.realm (id, access_code_lifespan, user_action_lifespan, access_token_lifespan, account_theme, admin_theme, email_theme, enabled, events_enabled, events_expiration, login_theme, name, not_before, password_policy, registration_allowed, remember_me, reset_password_allowed, social, ssl_required, sso_idle_timeout, sso_max_lifespan, update_profile_on_soc_login, verify_email, master_admin_client, login_lifespan, internationalization_enabled, default_locale, reg_email_as_username, admin_events_enabled, admin_events_details_enabled, edit_username_allowed, otp_policy_counter, otp_policy_window, otp_policy_period, otp_policy_digits, otp_policy_alg, otp_policy_type, browser_flow, registration_flow, direct_grant_flow, reset_credentials_flow, client_auth_flow, offline_session_idle_timeout, revoke_refresh_token, access_token_life_implicit, login_with_email_allowed, duplicate_emails_allowed, docker_auth_flow, refresh_token_max_reuse, allow_user_managed_access, sso_max_lifespan_remember_me, sso_idle_timeout_remember_me, default_role) FROM stdin;
2522a2b1-50e1-43f3-947e-15bab8e65d6a	60	300	60	\N	\N	\N	t	f	0	\N	master	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	0ad49e2d-3816-4fd1-9c25-01a1b7d65d25	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	28d70fa4-2075-4806-a4bc-a1aef12f65f4	522536ed-6210-4135-8d84-97e88d0fd2cb	5fc6a273-731f-48d8-a596-f3c967462384	4c78fa22-7ad5-41e5-b4ee-00b3cae0c6ff	254fbf8c-fdf8-405d-9c48-051945d135c5	2592000	f	900	t	f	08b3cedb-84fe-4dfb-8f95-54973c56d5e2	0	f	0	0	0794b2aa-e7c5-4511-b7e4-b70e017ee375
\.


--
-- Data for Name: realm_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.realm_attribute (name, realm_id, value) FROM stdin;
_browser_header.contentSecurityPolicyReportOnly	2522a2b1-50e1-43f3-947e-15bab8e65d6a	
_browser_header.xContentTypeOptions	2522a2b1-50e1-43f3-947e-15bab8e65d6a	nosniff
_browser_header.referrerPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	no-referrer
_browser_header.xRobotsTag	2522a2b1-50e1-43f3-947e-15bab8e65d6a	none
_browser_header.xFrameOptions	2522a2b1-50e1-43f3-947e-15bab8e65d6a	SAMEORIGIN
_browser_header.contentSecurityPolicy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.strictTransportSecurity	2522a2b1-50e1-43f3-947e-15bab8e65d6a	max-age=31536000; includeSubDomains
bruteForceProtected	2522a2b1-50e1-43f3-947e-15bab8e65d6a	false
permanentLockout	2522a2b1-50e1-43f3-947e-15bab8e65d6a	false
maxTemporaryLockouts	2522a2b1-50e1-43f3-947e-15bab8e65d6a	0
bruteForceStrategy	2522a2b1-50e1-43f3-947e-15bab8e65d6a	MULTIPLE
maxFailureWaitSeconds	2522a2b1-50e1-43f3-947e-15bab8e65d6a	900
minimumQuickLoginWaitSeconds	2522a2b1-50e1-43f3-947e-15bab8e65d6a	60
waitIncrementSeconds	2522a2b1-50e1-43f3-947e-15bab8e65d6a	60
quickLoginCheckMilliSeconds	2522a2b1-50e1-43f3-947e-15bab8e65d6a	1000
maxDeltaTimeSeconds	2522a2b1-50e1-43f3-947e-15bab8e65d6a	43200
failureFactor	2522a2b1-50e1-43f3-947e-15bab8e65d6a	30
realmReusableOtpCode	2522a2b1-50e1-43f3-947e-15bab8e65d6a	false
firstBrokerLoginFlowId	2522a2b1-50e1-43f3-947e-15bab8e65d6a	cc584f3f-d6e7-4aeb-a371-a931115eec16
displayName	2522a2b1-50e1-43f3-947e-15bab8e65d6a	Keycloak
displayNameHtml	2522a2b1-50e1-43f3-947e-15bab8e65d6a	<div class="kc-logo-text"><span>Keycloak</span></div>
defaultSignatureAlgorithm	2522a2b1-50e1-43f3-947e-15bab8e65d6a	RS256
offlineSessionMaxLifespanEnabled	2522a2b1-50e1-43f3-947e-15bab8e65d6a	false
offlineSessionMaxLifespan	2522a2b1-50e1-43f3-947e-15bab8e65d6a	5184000
\.


--
-- Data for Name: realm_default_groups; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.realm_default_groups (realm_id, group_id) FROM stdin;
\.


--
-- Data for Name: realm_enabled_event_types; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.realm_enabled_event_types (realm_id, value) FROM stdin;
\.


--
-- Data for Name: realm_events_listeners; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.realm_events_listeners (realm_id, value) FROM stdin;
2522a2b1-50e1-43f3-947e-15bab8e65d6a	jboss-logging
\.


--
-- Data for Name: realm_localizations; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.realm_localizations (realm_id, locale, texts) FROM stdin;
\.


--
-- Data for Name: realm_required_credential; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.realm_required_credential (type, form_label, input, secret, realm_id) FROM stdin;
password	password	t	t	2522a2b1-50e1-43f3-947e-15bab8e65d6a
\.


--
-- Data for Name: realm_smtp_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.realm_smtp_config (realm_id, value, name) FROM stdin;
\.


--
-- Data for Name: realm_supported_locales; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.realm_supported_locales (realm_id, value) FROM stdin;
\.


--
-- Data for Name: redirect_uris; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.redirect_uris (client_id, value) FROM stdin;
822c0def-a413-405f-ada7-901a19f0e14a	/realms/master/account/*
d3fd2493-8a37-4cf7-9c70-b05841741543	/realms/master/account/*
d1318f74-13c0-4a48-a30a-08b679c21f15	/admin/master/console/*
7866504f-df5b-40ad-8128-3ac0e63fc85a	/*
\.


--
-- Data for Name: required_action_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.required_action_config (required_action_id, value, name) FROM stdin;
\.


--
-- Data for Name: required_action_provider; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.required_action_provider (id, alias, name, realm_id, enabled, default_action, provider_id, priority) FROM stdin;
d752b993-d4ae-4312-90ea-a1000b92b65d	VERIFY_EMAIL	Verify Email	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	VERIFY_EMAIL	50
b69753d9-9f14-4855-8bf3-c1e4bf1cb9d7	UPDATE_PROFILE	Update Profile	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	UPDATE_PROFILE	40
d2ec93e7-cc69-43a5-a129-60e441b4682b	CONFIGURE_TOTP	Configure OTP	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	CONFIGURE_TOTP	10
4797dd81-846a-4ef7-b918-f3e24e5d8a17	UPDATE_PASSWORD	Update Password	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	UPDATE_PASSWORD	30
10da2661-988d-472a-b72f-0fba0370139d	TERMS_AND_CONDITIONS	Terms and Conditions	2522a2b1-50e1-43f3-947e-15bab8e65d6a	f	f	TERMS_AND_CONDITIONS	20
0cd9e5e8-6061-4fe7-a4c1-09a931cf3c40	delete_account	Delete Account	2522a2b1-50e1-43f3-947e-15bab8e65d6a	f	f	delete_account	60
4701b037-5f0a-4cf1-9a9c-80ccf8b666be	delete_credential	Delete Credential	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	delete_credential	110
851bcbdf-796f-4431-8ce4-b27afdc1e0e4	update_user_locale	Update User Locale	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	update_user_locale	1000
ea6233ff-7f62-4816-9cac-34e847eff86d	UPDATE_EMAIL	Update Email	2522a2b1-50e1-43f3-947e-15bab8e65d6a	f	f	UPDATE_EMAIL	70
3db5dcdc-2dce-4b70-8cd5-8f0ee22dad56	CONFIGURE_RECOVERY_AUTHN_CODES	Recovery Authentication Codes	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	CONFIGURE_RECOVERY_AUTHN_CODES	130
b381077a-a5d0-4cfd-b323-026cac495fe9	webauthn-register	Webauthn Register	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	webauthn-register	80
31342751-1fe6-49ff-8cb3-c75a985ba495	webauthn-register-passwordless	Webauthn Register Passwordless	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	webauthn-register-passwordless	90
4fa8feec-a3f9-4cda-b9c8-e7f7f6f8eb17	VERIFY_PROFILE	Verify Profile	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	VERIFY_PROFILE	100
694adde4-4023-4ead-980f-4ea51ff9af26	idp_link	Linking Identity Provider	2522a2b1-50e1-43f3-947e-15bab8e65d6a	t	f	idp_link	120
\.


--
-- Data for Name: resource_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.resource_attribute (id, name, value, resource_id) FROM stdin;
\.


--
-- Data for Name: resource_policy; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.resource_policy (resource_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_scope; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.resource_scope (resource_id, scope_id) FROM stdin;
\.


--
-- Data for Name: resource_server; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.resource_server (id, allow_rs_remote_mgmt, policy_enforce_mode, decision_strategy) FROM stdin;
\.


--
-- Data for Name: resource_server_perm_ticket; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.resource_server_perm_ticket (id, owner, requester, created_timestamp, granted_timestamp, resource_id, scope_id, resource_server_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_server_policy; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.resource_server_policy (id, name, description, type, decision_strategy, logic, resource_server_id, owner) FROM stdin;
\.


--
-- Data for Name: resource_server_resource; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.resource_server_resource (id, name, type, icon_uri, owner, resource_server_id, owner_managed_access, display_name) FROM stdin;
\.


--
-- Data for Name: resource_server_scope; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.resource_server_scope (id, name, icon_uri, resource_server_id, display_name) FROM stdin;
\.


--
-- Data for Name: resource_uris; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.resource_uris (resource_id, value) FROM stdin;
\.


--
-- Data for Name: revoked_token; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.revoked_token (id, expire) FROM stdin;
\.


--
-- Data for Name: role_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.role_attribute (id, role_id, name, value) FROM stdin;
\.


--
-- Data for Name: scope_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.scope_mapping (client_id, role_id) FROM stdin;
d3fd2493-8a37-4cf7-9c70-b05841741543	cbcb44e1-28e9-4579-816f-0b5c0f4f5bcf
d3fd2493-8a37-4cf7-9c70-b05841741543	dff8d26f-16bb-4c9d-9295-8800a2a6adf4
\.


--
-- Data for Name: scope_policy; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.scope_policy (scope_id, policy_id) FROM stdin;
\.


--
-- Data for Name: server_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.server_config (server_config_key, value, version) FROM stdin;
\.


--
-- Data for Name: user_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_attribute (name, value, user_id, id, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
is_temporary_admin	true	24901224-b171-470a-9f02-ce1f78be5622	8a5df4c1-4c6b-443c-80d2-e6c77fa299e2	\N	\N	\N
\.


--
-- Data for Name: user_consent; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_consent (id, client_id, user_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: user_consent_client_scope; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_consent_client_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: user_entity; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_entity (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before) FROM stdin;
24901224-b171-470a-9f02-ce1f78be5622	\N	3f069c6a-9247-41b4-8635-e54ec864e194	f	t	\N	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	admin	1760528660951	\N	0
fa26b769-cac6-49b4-8891-04ec02e2d47d	\N	7e52bc9d-59b7-463a-a207-61454721f452	f	t	\N	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	service-account-admin-cli	1760528870779	99c140d7-b340-4425-9fde-800f2f552715	0
564dccad-9599-4053-a861-83d405a5762d	user@test.test	user@test.test	f	t	\N	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	usertest	1760528937131	\N	0
330cb809-7940-4279-89cc-538204eb0c83	\N	742703df-5d85-4b3e-9d2b-72f4fc14ff5a	f	t	\N	\N	\N	2522a2b1-50e1-43f3-947e-15bab8e65d6a	service-account-iwa-client	1760533841780	7866504f-df5b-40ad-8128-3ac0e63fc85a	0
\.


--
-- Data for Name: user_federation_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_federation_config (user_federation_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_federation_mapper (id, name, federation_provider_id, federation_mapper_type, realm_id) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper_config; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_federation_mapper_config (user_federation_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_provider; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_federation_provider (id, changed_sync_period, display_name, full_sync_period, last_sync, priority, provider_name, realm_id) FROM stdin;
\.


--
-- Data for Name: user_group_membership; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_group_membership (group_id, user_id, membership_type) FROM stdin;
\.


--
-- Data for Name: user_required_action; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_required_action (user_id, required_action) FROM stdin;
\.


--
-- Data for Name: user_role_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.user_role_mapping (role_id, user_id) FROM stdin;
0794b2aa-e7c5-4511-b7e4-b70e017ee375	24901224-b171-470a-9f02-ce1f78be5622
7226339e-3ab6-47c4-95ec-3737455f18b9	24901224-b171-470a-9f02-ce1f78be5622
0794b2aa-e7c5-4511-b7e4-b70e017ee375	fa26b769-cac6-49b4-8891-04ec02e2d47d
0794b2aa-e7c5-4511-b7e4-b70e017ee375	564dccad-9599-4053-a861-83d405a5762d
1ea03edf-1ef8-4239-b3b9-6987ef11c040	fa26b769-cac6-49b4-8891-04ec02e2d47d
69ef72dd-84a6-49c9-ba40-cc460863e8e3	fa26b769-cac6-49b4-8891-04ec02e2d47d
0794b2aa-e7c5-4511-b7e4-b70e017ee375	330cb809-7940-4279-89cc-538204eb0c83
\.


--
-- Data for Name: web_origins; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.web_origins (client_id, value) FROM stdin;
d1318f74-13c0-4a48-a30a-08b679c21f15	+
7866504f-df5b-40ad-8128-3ac0e63fc85a	/*
\.


--
-- Data for Name: workflow_state; Type: TABLE DATA; Schema: public; Owner: keycloak_user
--

COPY public.workflow_state (execution_id, resource_id, workflow_id, workflow_provider_id, resource_type, scheduled_step_id, scheduled_step_timestamp) FROM stdin;
\.


--
-- Name: org_domain ORG_DOMAIN_pkey; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.org_domain
    ADD CONSTRAINT "ORG_DOMAIN_pkey" PRIMARY KEY (id, name);


--
-- Name: org ORG_pkey; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT "ORG_pkey" PRIMARY KEY (id);


--
-- Name: server_config SERVER_CONFIG_pkey; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.server_config
    ADD CONSTRAINT "SERVER_CONFIG_pkey" PRIMARY KEY (server_config_key);


--
-- Name: keycloak_role UK_J3RWUVD56ONTGSUHOGM184WW2-2; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT "UK_J3RWUVD56ONTGSUHOGM184WW2-2" UNIQUE (name, client_realm_constraint);


--
-- Name: client_auth_flow_bindings c_cli_flow_bind; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_auth_flow_bindings
    ADD CONSTRAINT c_cli_flow_bind PRIMARY KEY (client_id, binding_name);


--
-- Name: client_scope_client c_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_scope_client
    ADD CONSTRAINT c_cli_scope_bind PRIMARY KEY (client_id, scope_id);


--
-- Name: client_initial_access cnstr_client_init_acc_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT cnstr_client_init_acc_pk PRIMARY KEY (id);


--
-- Name: realm_default_groups con_group_id_def_groups; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT con_group_id_def_groups UNIQUE (group_id);


--
-- Name: broker_link constr_broker_link_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.broker_link
    ADD CONSTRAINT constr_broker_link_pk PRIMARY KEY (identity_provider, user_id);


--
-- Name: component_config constr_component_config_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT constr_component_config_pk PRIMARY KEY (id);


--
-- Name: component constr_component_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT constr_component_pk PRIMARY KEY (id);


--
-- Name: fed_user_required_action constr_fed_required_action; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.fed_user_required_action
    ADD CONSTRAINT constr_fed_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: fed_user_attribute constr_fed_user_attr_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.fed_user_attribute
    ADD CONSTRAINT constr_fed_user_attr_pk PRIMARY KEY (id);


--
-- Name: fed_user_consent constr_fed_user_consent_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.fed_user_consent
    ADD CONSTRAINT constr_fed_user_consent_pk PRIMARY KEY (id);


--
-- Name: fed_user_credential constr_fed_user_cred_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.fed_user_credential
    ADD CONSTRAINT constr_fed_user_cred_pk PRIMARY KEY (id);


--
-- Name: fed_user_group_membership constr_fed_user_group; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.fed_user_group_membership
    ADD CONSTRAINT constr_fed_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: fed_user_role_mapping constr_fed_user_role; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.fed_user_role_mapping
    ADD CONSTRAINT constr_fed_user_role PRIMARY KEY (role_id, user_id);


--
-- Name: federated_user constr_federated_user; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.federated_user
    ADD CONSTRAINT constr_federated_user PRIMARY KEY (id);


--
-- Name: realm_default_groups constr_realm_default_groups; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT constr_realm_default_groups PRIMARY KEY (realm_id, group_id);


--
-- Name: realm_enabled_event_types constr_realm_enabl_event_types; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT constr_realm_enabl_event_types PRIMARY KEY (realm_id, value);


--
-- Name: realm_events_listeners constr_realm_events_listeners; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT constr_realm_events_listeners PRIMARY KEY (realm_id, value);


--
-- Name: realm_supported_locales constr_realm_supported_locales; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT constr_realm_supported_locales PRIMARY KEY (realm_id, value);


--
-- Name: identity_provider constraint_2b; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT constraint_2b PRIMARY KEY (internal_id);


--
-- Name: client_attributes constraint_3c; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT constraint_3c PRIMARY KEY (client_id, name);


--
-- Name: event_entity constraint_4; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.event_entity
    ADD CONSTRAINT constraint_4 PRIMARY KEY (id);


--
-- Name: federated_identity constraint_40; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id);


--
-- Name: realm constraint_4a; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT constraint_4a PRIMARY KEY (id);


--
-- Name: user_federation_provider constraint_5c; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT constraint_5c PRIMARY KEY (id);


--
-- Name: client constraint_7; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT constraint_7 PRIMARY KEY (id);


--
-- Name: scope_mapping constraint_81; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT constraint_81 PRIMARY KEY (client_id, role_id);


--
-- Name: client_node_registrations constraint_84; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT constraint_84 PRIMARY KEY (client_id, name);


--
-- Name: realm_attribute constraint_9; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT constraint_9 PRIMARY KEY (name, realm_id);


--
-- Name: realm_required_credential constraint_92; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT constraint_92 PRIMARY KEY (realm_id, type);


--
-- Name: keycloak_role constraint_a; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT constraint_a PRIMARY KEY (id);


--
-- Name: admin_event_entity constraint_admin_event_entity; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.admin_event_entity
    ADD CONSTRAINT constraint_admin_event_entity PRIMARY KEY (id);


--
-- Name: authenticator_config_entry constraint_auth_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.authenticator_config_entry
    ADD CONSTRAINT constraint_auth_cfg_pk PRIMARY KEY (authenticator_id, name);


--
-- Name: authentication_execution constraint_auth_exec_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT constraint_auth_exec_pk PRIMARY KEY (id);


--
-- Name: authentication_flow constraint_auth_flow_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT constraint_auth_flow_pk PRIMARY KEY (id);


--
-- Name: authenticator_config constraint_auth_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT constraint_auth_pk PRIMARY KEY (id);


--
-- Name: user_role_mapping constraint_c; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT constraint_c PRIMARY KEY (role_id, user_id);


--
-- Name: composite_role constraint_composite_role; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT constraint_composite_role PRIMARY KEY (composite, child_role);


--
-- Name: identity_provider_config constraint_d; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT constraint_d PRIMARY KEY (identity_provider_id, name);


--
-- Name: policy_config constraint_dpc; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT constraint_dpc PRIMARY KEY (policy_id, name);


--
-- Name: realm_smtp_config constraint_e; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT constraint_e PRIMARY KEY (realm_id, name);


--
-- Name: credential constraint_f; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT constraint_f PRIMARY KEY (id);


--
-- Name: user_federation_config constraint_f9; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT constraint_f9 PRIMARY KEY (user_federation_provider_id, name);


--
-- Name: resource_server_perm_ticket constraint_fapmt; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT constraint_fapmt PRIMARY KEY (id);


--
-- Name: resource_server_resource constraint_farsr; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT constraint_farsr PRIMARY KEY (id);


--
-- Name: resource_server_policy constraint_farsrp; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT constraint_farsrp PRIMARY KEY (id);


--
-- Name: associated_policy constraint_farsrpap; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT constraint_farsrpap PRIMARY KEY (policy_id, associated_policy_id);


--
-- Name: resource_policy constraint_farsrpp; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT constraint_farsrpp PRIMARY KEY (resource_id, policy_id);


--
-- Name: resource_server_scope constraint_farsrs; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT constraint_farsrs PRIMARY KEY (id);


--
-- Name: resource_scope constraint_farsrsp; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT constraint_farsrsp PRIMARY KEY (resource_id, scope_id);


--
-- Name: scope_policy constraint_farsrsps; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT constraint_farsrsps PRIMARY KEY (scope_id, policy_id);


--
-- Name: user_entity constraint_fb; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT constraint_fb PRIMARY KEY (id);


--
-- Name: user_federation_mapper_config constraint_fedmapper_cfg_pm; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT constraint_fedmapper_cfg_pm PRIMARY KEY (user_federation_mapper_id, name);


--
-- Name: user_federation_mapper constraint_fedmapperpm; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT constraint_fedmapperpm PRIMARY KEY (id);


--
-- Name: fed_user_consent_cl_scope constraint_fgrntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.fed_user_consent_cl_scope
    ADD CONSTRAINT constraint_fgrntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent_client_scope constraint_grntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT constraint_grntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent constraint_grntcsnt_pm; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT constraint_grntcsnt_pm PRIMARY KEY (id);


--
-- Name: keycloak_group constraint_group; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT constraint_group PRIMARY KEY (id);


--
-- Name: group_attribute constraint_group_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT constraint_group_attribute_pk PRIMARY KEY (id);


--
-- Name: group_role_mapping constraint_group_role; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT constraint_group_role PRIMARY KEY (role_id, group_id);


--
-- Name: identity_provider_mapper constraint_idpm; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT constraint_idpm PRIMARY KEY (id);


--
-- Name: idp_mapper_config constraint_idpmconfig; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT constraint_idpmconfig PRIMARY KEY (idp_mapper_id, name);


--
-- Name: jgroups_ping constraint_jgroups_ping; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.jgroups_ping
    ADD CONSTRAINT constraint_jgroups_ping PRIMARY KEY (address);


--
-- Name: migration_model constraint_migmod; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT constraint_migmod PRIMARY KEY (id);


--
-- Name: offline_client_session constraint_offl_cl_ses_pk3; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.offline_client_session
    ADD CONSTRAINT constraint_offl_cl_ses_pk3 PRIMARY KEY (user_session_id, client_id, client_storage_provider, external_client_id, offline_flag);


--
-- Name: offline_user_session constraint_offl_us_ses_pk2; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.offline_user_session
    ADD CONSTRAINT constraint_offl_us_ses_pk2 PRIMARY KEY (user_session_id, offline_flag);


--
-- Name: protocol_mapper constraint_pcm; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT constraint_pcm PRIMARY KEY (id);


--
-- Name: protocol_mapper_config constraint_pmconfig; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT constraint_pmconfig PRIMARY KEY (protocol_mapper_id, name);


--
-- Name: redirect_uris constraint_redirect_uris; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT constraint_redirect_uris PRIMARY KEY (client_id, value);


--
-- Name: required_action_config constraint_req_act_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.required_action_config
    ADD CONSTRAINT constraint_req_act_cfg_pk PRIMARY KEY (required_action_id, name);


--
-- Name: required_action_provider constraint_req_act_prv_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT constraint_req_act_prv_pk PRIMARY KEY (id);


--
-- Name: user_required_action constraint_required_action; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT constraint_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: resource_uris constraint_resour_uris_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT constraint_resour_uris_pk PRIMARY KEY (resource_id, value);


--
-- Name: role_attribute constraint_role_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT constraint_role_attribute_pk PRIMARY KEY (id);


--
-- Name: revoked_token constraint_rt; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.revoked_token
    ADD CONSTRAINT constraint_rt PRIMARY KEY (id);


--
-- Name: user_attribute constraint_user_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT constraint_user_attribute_pk PRIMARY KEY (id);


--
-- Name: user_group_membership constraint_user_group; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT constraint_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: web_origins constraint_web_origins; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT constraint_web_origins PRIMARY KEY (client_id, value);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: client_scope_attributes pk_cl_tmpl_attr; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT pk_cl_tmpl_attr PRIMARY KEY (scope_id, name);


--
-- Name: client_scope pk_cli_template; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT pk_cli_template PRIMARY KEY (id);


--
-- Name: resource_server pk_resource_server; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server
    ADD CONSTRAINT pk_resource_server PRIMARY KEY (id);


--
-- Name: client_scope_role_mapping pk_template_scope; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT pk_template_scope PRIMARY KEY (scope_id, role_id);


--
-- Name: workflow_state pk_workflow_state; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.workflow_state
    ADD CONSTRAINT pk_workflow_state PRIMARY KEY (execution_id);


--
-- Name: default_client_scope r_def_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT r_def_cli_scope_bind PRIMARY KEY (realm_id, scope_id);


--
-- Name: realm_localizations realm_localizations_pkey; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_localizations
    ADD CONSTRAINT realm_localizations_pkey PRIMARY KEY (realm_id, locale);


--
-- Name: resource_attribute res_attr_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT res_attr_pk PRIMARY KEY (id);


--
-- Name: keycloak_group sibling_names; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT sibling_names UNIQUE (realm_id, parent_group, name);


--
-- Name: identity_provider uk_2daelwnibji49avxsrtuf6xj33; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT uk_2daelwnibji49avxsrtuf6xj33 UNIQUE (provider_alias, realm_id);


--
-- Name: client uk_b71cjlbenv945rb6gcon438at; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_b71cjlbenv945rb6gcon438at UNIQUE (realm_id, client_id);


--
-- Name: client_scope uk_cli_scope; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT uk_cli_scope UNIQUE (realm_id, name);


--
-- Name: user_entity uk_dykn684sl8up1crfei6eckhd7; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_dykn684sl8up1crfei6eckhd7 UNIQUE (realm_id, email_constraint);


--
-- Name: user_consent uk_external_consent; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_external_consent UNIQUE (client_storage_provider, external_client_id, user_id);


--
-- Name: resource_server_resource uk_frsr6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5ha6 UNIQUE (name, owner, resource_server_id);


--
-- Name: resource_server_perm_ticket uk_frsr6t700s9v50bu18ws5pmt; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5pmt UNIQUE (owner, requester, resource_server_id, resource_id, scope_id);


--
-- Name: resource_server_policy uk_frsrpt700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT uk_frsrpt700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: resource_server_scope uk_frsrst700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT uk_frsrst700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: user_consent uk_local_consent; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_local_consent UNIQUE (client_id, user_id);


--
-- Name: migration_model uk_migration_update_time; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT uk_migration_update_time UNIQUE (update_time);


--
-- Name: migration_model uk_migration_version; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT uk_migration_version UNIQUE (version);


--
-- Name: org uk_org_alias; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_alias UNIQUE (realm_id, alias);


--
-- Name: org uk_org_group; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_group UNIQUE (group_id);


--
-- Name: org uk_org_name; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_name UNIQUE (realm_id, name);


--
-- Name: realm uk_orvsdmla56612eaefiq6wl5oi; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT uk_orvsdmla56612eaefiq6wl5oi UNIQUE (name);


--
-- Name: user_entity uk_ru8tt6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_ru8tt6t700s9v50bu18ws5ha6 UNIQUE (realm_id, username);


--
-- Name: workflow_state uq_workflow_resource; Type: CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.workflow_state
    ADD CONSTRAINT uq_workflow_resource UNIQUE (workflow_id, resource_id);


--
-- Name: fed_user_attr_long_values; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX fed_user_attr_long_values ON public.fed_user_attribute USING btree (long_value_hash, name);


--
-- Name: fed_user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX fed_user_attr_long_values_lower_case ON public.fed_user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: idx_admin_event_time; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_admin_event_time ON public.admin_event_entity USING btree (realm_id, admin_event_time);


--
-- Name: idx_assoc_pol_assoc_pol_id; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_assoc_pol_assoc_pol_id ON public.associated_policy USING btree (associated_policy_id);


--
-- Name: idx_auth_config_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_auth_config_realm ON public.authenticator_config USING btree (realm_id);


--
-- Name: idx_auth_exec_flow; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_auth_exec_flow ON public.authentication_execution USING btree (flow_id);


--
-- Name: idx_auth_exec_realm_flow; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_auth_exec_realm_flow ON public.authentication_execution USING btree (realm_id, flow_id);


--
-- Name: idx_auth_flow_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_auth_flow_realm ON public.authentication_flow USING btree (realm_id);


--
-- Name: idx_cl_clscope; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_cl_clscope ON public.client_scope_client USING btree (scope_id);


--
-- Name: idx_client_att_by_name_value; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_client_att_by_name_value ON public.client_attributes USING btree (name, substr(value, 1, 255));


--
-- Name: idx_client_id; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_client_id ON public.client USING btree (client_id);


--
-- Name: idx_client_init_acc_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_client_init_acc_realm ON public.client_initial_access USING btree (realm_id);


--
-- Name: idx_clscope_attrs; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_clscope_attrs ON public.client_scope_attributes USING btree (scope_id);


--
-- Name: idx_clscope_cl; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_clscope_cl ON public.client_scope_client USING btree (client_id);


--
-- Name: idx_clscope_protmap; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_clscope_protmap ON public.protocol_mapper USING btree (client_scope_id);


--
-- Name: idx_clscope_role; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_clscope_role ON public.client_scope_role_mapping USING btree (scope_id);


--
-- Name: idx_compo_config_compo; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_compo_config_compo ON public.component_config USING btree (component_id);


--
-- Name: idx_component_provider_type; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_component_provider_type ON public.component USING btree (provider_type);


--
-- Name: idx_component_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_component_realm ON public.component USING btree (realm_id);


--
-- Name: idx_composite; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_composite ON public.composite_role USING btree (composite);


--
-- Name: idx_composite_child; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_composite_child ON public.composite_role USING btree (child_role);


--
-- Name: idx_defcls_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_defcls_realm ON public.default_client_scope USING btree (realm_id);


--
-- Name: idx_defcls_scope; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_defcls_scope ON public.default_client_scope USING btree (scope_id);


--
-- Name: idx_event_entity_user_id_type; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_event_entity_user_id_type ON public.event_entity USING btree (user_id, type, event_time);


--
-- Name: idx_event_time; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_event_time ON public.event_entity USING btree (realm_id, event_time);


--
-- Name: idx_fedidentity_feduser; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fedidentity_feduser ON public.federated_identity USING btree (federated_user_id);


--
-- Name: idx_fedidentity_user; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fedidentity_user ON public.federated_identity USING btree (user_id);


--
-- Name: idx_fu_attribute; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_attribute ON public.fed_user_attribute USING btree (user_id, realm_id, name);


--
-- Name: idx_fu_cnsnt_ext; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_cnsnt_ext ON public.fed_user_consent USING btree (user_id, client_storage_provider, external_client_id);


--
-- Name: idx_fu_consent; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_consent ON public.fed_user_consent USING btree (user_id, client_id);


--
-- Name: idx_fu_consent_ru; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_consent_ru ON public.fed_user_consent USING btree (realm_id, user_id);


--
-- Name: idx_fu_credential; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_credential ON public.fed_user_credential USING btree (user_id, type);


--
-- Name: idx_fu_credential_ru; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_credential_ru ON public.fed_user_credential USING btree (realm_id, user_id);


--
-- Name: idx_fu_group_membership; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_group_membership ON public.fed_user_group_membership USING btree (user_id, group_id);


--
-- Name: idx_fu_group_membership_ru; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_group_membership_ru ON public.fed_user_group_membership USING btree (realm_id, user_id);


--
-- Name: idx_fu_required_action; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_required_action ON public.fed_user_required_action USING btree (user_id, required_action);


--
-- Name: idx_fu_required_action_ru; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_required_action_ru ON public.fed_user_required_action USING btree (realm_id, user_id);


--
-- Name: idx_fu_role_mapping; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_role_mapping ON public.fed_user_role_mapping USING btree (user_id, role_id);


--
-- Name: idx_fu_role_mapping_ru; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_fu_role_mapping_ru ON public.fed_user_role_mapping USING btree (realm_id, user_id);


--
-- Name: idx_group_att_by_name_value; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_group_att_by_name_value ON public.group_attribute USING btree (name, ((value)::character varying(250)));


--
-- Name: idx_group_attr_group; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_group_attr_group ON public.group_attribute USING btree (group_id);


--
-- Name: idx_group_role_mapp_group; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_group_role_mapp_group ON public.group_role_mapping USING btree (group_id);


--
-- Name: idx_id_prov_mapp_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_id_prov_mapp_realm ON public.identity_provider_mapper USING btree (realm_id);


--
-- Name: idx_ident_prov_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_ident_prov_realm ON public.identity_provider USING btree (realm_id);


--
-- Name: idx_idp_for_login; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_idp_for_login ON public.identity_provider USING btree (realm_id, enabled, link_only, hide_on_login, organization_id);


--
-- Name: idx_idp_realm_org; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_idp_realm_org ON public.identity_provider USING btree (realm_id, organization_id);


--
-- Name: idx_keycloak_role_client; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_keycloak_role_client ON public.keycloak_role USING btree (client);


--
-- Name: idx_keycloak_role_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_keycloak_role_realm ON public.keycloak_role USING btree (realm);


--
-- Name: idx_offline_uss_by_broker_session_id; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_offline_uss_by_broker_session_id ON public.offline_user_session USING btree (broker_session_id, realm_id);


--
-- Name: idx_offline_uss_by_last_session_refresh; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_offline_uss_by_last_session_refresh ON public.offline_user_session USING btree (realm_id, offline_flag, last_session_refresh);


--
-- Name: idx_offline_uss_by_user; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_offline_uss_by_user ON public.offline_user_session USING btree (user_id, realm_id, offline_flag);


--
-- Name: idx_org_domain_org_id; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_org_domain_org_id ON public.org_domain USING btree (org_id);


--
-- Name: idx_perm_ticket_owner; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_perm_ticket_owner ON public.resource_server_perm_ticket USING btree (owner);


--
-- Name: idx_perm_ticket_requester; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_perm_ticket_requester ON public.resource_server_perm_ticket USING btree (requester);


--
-- Name: idx_protocol_mapper_client; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_protocol_mapper_client ON public.protocol_mapper USING btree (client_id);


--
-- Name: idx_realm_attr_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_realm_attr_realm ON public.realm_attribute USING btree (realm_id);


--
-- Name: idx_realm_clscope; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_realm_clscope ON public.client_scope USING btree (realm_id);


--
-- Name: idx_realm_def_grp_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_realm_def_grp_realm ON public.realm_default_groups USING btree (realm_id);


--
-- Name: idx_realm_evt_list_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_realm_evt_list_realm ON public.realm_events_listeners USING btree (realm_id);


--
-- Name: idx_realm_evt_types_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_realm_evt_types_realm ON public.realm_enabled_event_types USING btree (realm_id);


--
-- Name: idx_realm_master_adm_cli; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_realm_master_adm_cli ON public.realm USING btree (master_admin_client);


--
-- Name: idx_realm_supp_local_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_realm_supp_local_realm ON public.realm_supported_locales USING btree (realm_id);


--
-- Name: idx_redir_uri_client; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_redir_uri_client ON public.redirect_uris USING btree (client_id);


--
-- Name: idx_req_act_prov_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_req_act_prov_realm ON public.required_action_provider USING btree (realm_id);


--
-- Name: idx_res_policy_policy; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_res_policy_policy ON public.resource_policy USING btree (policy_id);


--
-- Name: idx_res_scope_scope; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_res_scope_scope ON public.resource_scope USING btree (scope_id);


--
-- Name: idx_res_serv_pol_res_serv; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_res_serv_pol_res_serv ON public.resource_server_policy USING btree (resource_server_id);


--
-- Name: idx_res_srv_res_res_srv; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_res_srv_res_res_srv ON public.resource_server_resource USING btree (resource_server_id);


--
-- Name: idx_res_srv_scope_res_srv; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_res_srv_scope_res_srv ON public.resource_server_scope USING btree (resource_server_id);


--
-- Name: idx_rev_token_on_expire; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_rev_token_on_expire ON public.revoked_token USING btree (expire);


--
-- Name: idx_role_attribute; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_role_attribute ON public.role_attribute USING btree (role_id);


--
-- Name: idx_role_clscope; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_role_clscope ON public.client_scope_role_mapping USING btree (role_id);


--
-- Name: idx_scope_mapping_role; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_scope_mapping_role ON public.scope_mapping USING btree (role_id);


--
-- Name: idx_scope_policy_policy; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_scope_policy_policy ON public.scope_policy USING btree (policy_id);


--
-- Name: idx_update_time; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_update_time ON public.migration_model USING btree (update_time);


--
-- Name: idx_usconsent_clscope; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_usconsent_clscope ON public.user_consent_client_scope USING btree (user_consent_id);


--
-- Name: idx_usconsent_scope_id; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_usconsent_scope_id ON public.user_consent_client_scope USING btree (scope_id);


--
-- Name: idx_user_attribute; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_user_attribute ON public.user_attribute USING btree (user_id);


--
-- Name: idx_user_attribute_name; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_user_attribute_name ON public.user_attribute USING btree (name, value);


--
-- Name: idx_user_consent; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_user_consent ON public.user_consent USING btree (user_id);


--
-- Name: idx_user_credential; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_user_credential ON public.credential USING btree (user_id);


--
-- Name: idx_user_email; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_user_email ON public.user_entity USING btree (email);


--
-- Name: idx_user_group_mapping; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_user_group_mapping ON public.user_group_membership USING btree (user_id);


--
-- Name: idx_user_reqactions; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_user_reqactions ON public.user_required_action USING btree (user_id);


--
-- Name: idx_user_role_mapping; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_user_role_mapping ON public.user_role_mapping USING btree (user_id);


--
-- Name: idx_user_service_account; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_user_service_account ON public.user_entity USING btree (realm_id, service_account_client_link);


--
-- Name: idx_usr_fed_map_fed_prv; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_usr_fed_map_fed_prv ON public.user_federation_mapper USING btree (federation_provider_id);


--
-- Name: idx_usr_fed_map_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_usr_fed_map_realm ON public.user_federation_mapper USING btree (realm_id);


--
-- Name: idx_usr_fed_prv_realm; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_usr_fed_prv_realm ON public.user_federation_provider USING btree (realm_id);


--
-- Name: idx_web_orig_client; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_web_orig_client ON public.web_origins USING btree (client_id);


--
-- Name: idx_workflow_state_provider; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_workflow_state_provider ON public.workflow_state USING btree (resource_id, workflow_provider_id);


--
-- Name: idx_workflow_state_step; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX idx_workflow_state_step ON public.workflow_state USING btree (workflow_id, scheduled_step_id);


--
-- Name: user_attr_long_values; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX user_attr_long_values ON public.user_attribute USING btree (long_value_hash, name);


--
-- Name: user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: keycloak_user
--

CREATE INDEX user_attr_long_values_lower_case ON public.user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: identity_provider fk2b4ebc52ae5c3b34; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT fk2b4ebc52ae5c3b34 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_attributes fk3c47c64beacca966; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT fk3c47c64beacca966 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: federated_identity fk404288b92ef007a6; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT fk404288b92ef007a6 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_node_registrations fk4129723ba992f594; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT fk4129723ba992f594 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: redirect_uris fk_1burs8pb4ouj97h5wuppahv9f; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT fk_1burs8pb4ouj97h5wuppahv9f FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: user_federation_provider fk_1fj32f6ptolw2qy60cd8n01e8; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT fk_1fj32f6ptolw2qy60cd8n01e8 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_required_credential fk_5hg65lybevavkqfki3kponh9v; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT fk_5hg65lybevavkqfki3kponh9v FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_attribute fk_5hrm2vlf9ql5fu022kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: user_attribute fk_5hrm2vlf9ql5fu043kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu043kqepovbr FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: user_required_action fk_6qj3w1jw9cvafhe19bwsiuvmd; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT fk_6qj3w1jw9cvafhe19bwsiuvmd FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: keycloak_role fk_6vyqfe4cn4wlq8r6kt5vdsj5c; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT fk_6vyqfe4cn4wlq8r6kt5vdsj5c FOREIGN KEY (realm) REFERENCES public.realm(id);


--
-- Name: realm_smtp_config fk_70ej8xdxgxd0b9hh6180irr0o; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT fk_70ej8xdxgxd0b9hh6180irr0o FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_attribute fk_8shxd6l3e9atqukacxgpffptw; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT fk_8shxd6l3e9atqukacxgpffptw FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: composite_role fk_a63wvekftu8jo1pnj81e7mce2; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_a63wvekftu8jo1pnj81e7mce2 FOREIGN KEY (composite) REFERENCES public.keycloak_role(id);


--
-- Name: authentication_execution fk_auth_exec_flow; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_flow FOREIGN KEY (flow_id) REFERENCES public.authentication_flow(id);


--
-- Name: authentication_execution fk_auth_exec_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authentication_flow fk_auth_flow_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT fk_auth_flow_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authenticator_config fk_auth_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT fk_auth_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_role_mapping fk_c4fqv34p1mbylloxang7b1q3l; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_scope_attributes fk_cl_scope_attr_scope; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT fk_cl_scope_attr_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_scope_role_mapping fk_cl_scope_rm_scope; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT fk_cl_scope_rm_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: protocol_mapper fk_cli_scope_mapper; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_cli_scope_mapper FOREIGN KEY (client_scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_initial_access fk_client_init_acc_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT fk_client_init_acc_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: component_config fk_component_config; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT fk_component_config FOREIGN KEY (component_id) REFERENCES public.component(id);


--
-- Name: component fk_component_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT fk_component_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_default_groups fk_def_groups_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT fk_def_groups_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_mapper_config fk_fedmapper_cfg; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT fk_fedmapper_cfg FOREIGN KEY (user_federation_mapper_id) REFERENCES public.user_federation_mapper(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_fedprv; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_fedprv FOREIGN KEY (federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: associated_policy fk_frsr5s213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsr5s213xcx4wnkog82ssrfy FOREIGN KEY (associated_policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrasp13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrasp13xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog82sspmt; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82sspmt FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_resource fk_frsrho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog83sspmt; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog83sspmt FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog84sspmt; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog84sspmt FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: associated_policy fk_frsrpas14xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsrpas14xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrpass3xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrpass3xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_perm_ticket fk_frsrpo2128cx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrpo2128cx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_policy fk_frsrpo213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT fk_frsrpo213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_scope fk_frsrpos13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrpos13xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpos53xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpos53xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpp213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpp213xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_scope fk_frsrps213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrps213xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_scope fk_frsrso213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT fk_frsrso213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: composite_role fk_gr7thllb9lu8q4vqa4524jjy8; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_gr7thllb9lu8q4vqa4524jjy8 FOREIGN KEY (child_role) REFERENCES public.keycloak_role(id);


--
-- Name: user_consent_client_scope fk_grntcsnt_clsc_usc; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT fk_grntcsnt_clsc_usc FOREIGN KEY (user_consent_id) REFERENCES public.user_consent(id);


--
-- Name: user_consent fk_grntcsnt_user; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT fk_grntcsnt_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: group_attribute fk_group_attribute_group; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT fk_group_attribute_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: group_role_mapping fk_group_role_group; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT fk_group_role_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: realm_enabled_event_types fk_h846o4h0w8epx5nwedrf5y69j; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT fk_h846o4h0w8epx5nwedrf5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_events_listeners fk_h846o4h0w8epx5nxev9f5y69j; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT fk_h846o4h0w8epx5nxev9f5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: identity_provider_mapper fk_idpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT fk_idpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: idp_mapper_config fk_idpmconfig; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT fk_idpmconfig FOREIGN KEY (idp_mapper_id) REFERENCES public.identity_provider_mapper(id);


--
-- Name: web_origins fk_lojpho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT fk_lojpho213xcx4wnkog82ssrfy FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: scope_mapping fk_ouse064plmlr732lxjcn1q5f1; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT fk_ouse064plmlr732lxjcn1q5f1 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: protocol_mapper fk_pcm_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_pcm_realm FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: credential fk_pfyr0glasqyl0dei3kl69r6v0; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: protocol_mapper_config fk_pmconfig; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT fk_pmconfig FOREIGN KEY (protocol_mapper_id) REFERENCES public.protocol_mapper(id);


--
-- Name: default_client_scope fk_r_def_cli_scope_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT fk_r_def_cli_scope_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: required_action_provider fk_req_act_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT fk_req_act_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_uris fk_resource_server_uris; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT fk_resource_server_uris FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: role_attribute fk_role_attribute_id; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT fk_role_attribute_id FOREIGN KEY (role_id) REFERENCES public.keycloak_role(id);


--
-- Name: realm_supported_locales fk_supported_locales_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT fk_supported_locales_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_config fk_t13hpu1j94r2ebpekr39x5eu5; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT fk_t13hpu1j94r2ebpekr39x5eu5 FOREIGN KEY (user_federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_group_membership fk_user_group_user; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: policy_config fkdc34197cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT fkdc34197cf864c4e43 FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: identity_provider_config fkdc4897cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: keycloak_user
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT fkdc4897cf864c4e43 FOREIGN KEY (identity_provider_id) REFERENCES public.identity_provider(internal_id);


--
-- PostgreSQL database dump complete
--

\unrestrict hOVFiwUhud45vbFCXIacVVSnrrbD7PdndZmohJqdSFeqca6f11pGNUeEPRNEPmJ

