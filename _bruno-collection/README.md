# 🧪 Collection Bruno - IWA Microservices

## 📦 Importer la Collection

1. Ouvrir **Bruno**
2. **Open Collection** → Sélectionner `/back/bruno-collection/`
3. Sélectionner l'environnement **"Local"** (dropdown en haut à droite)

---

## 🚀 Démarrage Rapide

### 1️⃣ Login
```
Keycloak/Login
```
→ Récupère et sauvegarde automatiquement le token dans `{{ACCESS_TOKEN}}`

### 2️⃣ Test Gateway (Public)
```
Gateway/Health Check
Gateway/Test Endpoint
Gateway/Get Languages
Gateway/Get Specialisations
```
→ Aucune auth requise

### 3️⃣ Routes Utilisateur (Protégées)
```
User/Get My Profile
User/Update Profile
User/Get My Languages
User/Update Languages
User/Get My Specialisations
User/Update Specialisations
User/Upload Photo Base64
User/Delete Photo
```
→ Requiert le token (automatique)

---

## 📂 Structure

```
bruno-collection/
├── Keycloak/          # Authentification
├── Gateway/           # Endpoints publics
└── User/              # User Service (protégé)
```

---

## ⚙️ Configuration

Les variables sont dans `environments/Local.bru` :

```
GATEWAY_URL: http://localhost:8085
KEYCLOAK_URL: http://localhost:8080
KEYCLOAK_REALM: master
CLIENT_ID: admin-cli
```

---

## 🔍 Voir les Logs

### Kibana (Recommandé)
```
http://localhost:5601
```
1. Créer data view: `microservices-logs*`
2. Discover → Voir tous les logs en temps réel

### Docker Logs
```bash
# Gateway
docker logs gateway-service -f

# User Service
docker logs user-service -f
```

---

## 🎯 Workflow Typique

1. **Login** → `Keycloak/Login`
2. **Check Gateway** → `Gateway/Health Check`
3. **Test Public** → `Gateway/Get Languages`
4. **Get Profile** → `User/Get My Profile`
5. **Update** → `User/Update Profile`
6. **Vérifier logs** → Kibana

---

## 🐛 Troubleshooting

### 401 Unauthorized
→ Token expiré ou invalide → Re-login

### 403 Forbidden
→ Permissions insuffisantes ou Gateway secret invalide

### Connection refused
→ Service down → `docker-compose up -d`

---

## 📊 Logs Générés

Chaque requête génère des logs détaillés visibles dans Kibana :
- Gateway: Authentication, routing, rate limiting
- User-Service: CRUD operations
- Tous envoyés automatiquement à Kafka → Elasticsearch → Kibana

🚀 **Prêt pour tester !**
