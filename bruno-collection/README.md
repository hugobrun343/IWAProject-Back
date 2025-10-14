# 🧪 Collection Bruno - IWA Microservices

## 📦 Installation

### 1. Installer Bruno
```bash
# macOS
brew install bruno

# Ou télécharger depuis
https://www.usebruno.com/downloads
```

### 2. Importer la collection
1. Ouvrir Bruno
2. Cliquer sur **"Open Collection"**
3. Sélectionner le dossier `/back/bruno-collection`

### 3. Configurer l'environnement
1. Dans Bruno, aller dans **Environments**
2. Sélectionner **"Local"**
3. Modifier les variables si nécessaire :
   - `CLIENT_ID`: Votre client Keycloak
   - `CLIENT_SECRET`: Votre secret Keycloak
   - Les autres variables sont déjà configurées

---

## 🚀 Démarrage Rapide

### Ordre d'exécution recommandé :

#### 1. **Authentification** 
   → `Auth/1. Login - Get Token`
   - ✅ Met à jour automatiquement `ACCESS_TOKEN`
   - ✅ Utiliser vos credentials Keycloak

#### 2. **Health Check**
   → `Gateway/1. Health Check`
   - ✅ Vérifie que la Gateway fonctionne
   - ✅ Génère des logs dans Kibana

#### 3. **Endpoints Publics**
   → `Gateway/2. Get Languages (Public)`
   - ✅ Pas d'auth requise
   - ✅ Test du routing Gateway → User-Service

#### 4. **Profil Utilisateur**
   → `User Service/1. Get My Profile`
   - ✅ Requiert authentification
   - ✅ Génère logs détaillés (Gateway + User-Service)

#### 5. **Modifications**
   - `User Service/2. Update My Profile`
   - `User Service/3. Upload Photo Base64`
   - `User Service/4. Update My Languages`
   - `User Service/5. Update My Specialisations`

#### 6. **Test Sécurité**
   → `Security Tests/1. Test Direct Access (Should Fail)`
   - ✅ Doit retourner 403 Forbidden
   - ✅ Vérifie que le bypass Gateway est impossible

---

## 📊 Vérifier les Logs

### Kibana (Recommandé)
```
URL: http://localhost:5601

1. Aller dans "Discover"
2. Sélectionner l'index "microservices-logs*"
3. Filtrer par :
   - service: "Gateway-Service"
   - service: "User-Service"
   - level: "ERROR" | "WARN" | "INFO"
   - message: "user: john"
```

### Logs Docker (Direct)
```bash
# Gateway logs
docker logs gateway-service -f

# User-Service logs
docker logs user-service -f

# Tous les logs via Kafka
docker exec -it kafka kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic microservices-logs \
  --from-beginning
```

---

## 🎯 Logs Générés par Requête

### `Auth/1. Login - Get Token`
- Keycloak authentication logs

### `Gateway/1. Health Check`
- `log.debug("Health check requested")`
- `log.info("Health check completed - Status: UP")`

### `Gateway/2. Get Languages (Public)`
- `log.debug("Unauthenticated request to: /api/languages")`

### `User Service/1. Get My Profile`
- Gateway: `log.info("Authenticated request from user: john")`
- Gateway: `log.debug("Processing request: GET /api/users/me")`
- User: `log.info("GET /users/me - User: john")`
- User: `log.debug("Fetching user data for username: john")`
- User: `log.info("Successfully retrieved user data for: john")`

### `User Service/2. Update My Profile`
- Gateway: `log.info("Authenticated request from user: john")`
- User: `log.info("PATCH /users/me - User: john")`
- User: `log.info("Updating profile for user: john with fields: [firstName, lastName, email]")`
- User: `log.info("Profile successfully updated for user: john")`

### `User Service/3. Upload Photo Base64`
- User: `log.info("POST /users/me/photo - User: john")`
- User: `log.info("Uploading photo for user: john (size: X bytes)")`
- User: `log.info("Photo successfully uploaded for user: john")`

### `User Service/4. Update My Languages`
- User: `log.info("PUT /users/me/languages - User: john")`
- User: `log.info("Replacing languages for user: john with: [French, English, Spanish]")`
- User: `log.info("Languages successfully updated for user: john")`

### `User Service/5. Update My Specialisations`
- User: `log.info("PUT /users/me/specialisations - User: john")`
- User: `log.info("Replacing specialisations for user: john with: [...]")`
- User: `log.info("Specialisations successfully updated for user: john")`

---

## 🔒 Sécurité

### Headers Automatiques (Gateway)
La Gateway ajoute automatiquement ces headers à chaque requête :
- `X-Username`: Extrait du JWT (claim: preferred_username)
- `X-User-Id`: Extrait du JWT (claim: sub)
- `X-Gateway-Secret`: Secret partagé pour authentifier la Gateway

### Microservices
Chaque microservice :
1. ✅ Vérifie le header `X-Gateway-Secret`
2. ✅ Refuse les requêtes directes (sans secret)
3. ✅ Fait confiance à `X-Username` (déjà validé par Gateway)

### Rate Limiting
- 100 requêtes/minute par IP
- Status: 429 Too Many Requests si dépassé
- Log: `log.warn("Rate limit exceeded for IP: X.X.X.X (101 requests)")`

---

## 🐛 Troubleshooting

### Token Expiré
```
Status: 401 Unauthorized
```
**Solution:** Re-exécuter `Auth/1. Login - Get Token`

### Rate Limit
```
Status: 429 Too Many Requests
```
**Solution:** Attendre 1 minute

### Accès Direct Bloqué
```
Status: 403 Forbidden
{"error": "Access Denied: Invalid Gateway Secret"}
```
**Solution:** Normal ! Utiliser la Gateway (port 8085)

### Service Down
```
Connection refused
```
**Solution:** Vérifier Docker
```bash
docker ps | grep "gateway\|user-service"
docker-compose up -d
```

---

## 📝 Variables d'Environnement

### Automatiques
- `ACCESS_TOKEN`: Rempli par script post-response du login
- `REFRESH_TOKEN`: Rempli par script post-response du login

### À Configurer
- `GATEWAY_URL`: http://localhost:8085
- `KEYCLOAK_URL`: http://localhost:8080
- `KEYCLOAK_REALM`: Votre realm Keycloak
- `CLIENT_ID`: Votre client ID
- `CLIENT_SECRET`: Votre client secret

---

## 🎉 Résultat

Avec cette collection, tu peux :
- ✅ Tester tous les endpoints
- ✅ Générer des logs détaillés
- ✅ Vérifier la sécurité (Gateway secret)
- ✅ Visualiser les logs dans Kibana
- ✅ Debugger facilement

**Tous les logs sont automatiquement envoyés à Kafka → Elasticsearch → Kibana !** 🚀

---

## 📚 Documentation Complète

Pour plus de détails, voir `/back/bruno-examples.md`

