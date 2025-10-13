# 📊 Log-Service - Centralisation des logs avec ELK + Kafka

## 🏗️ Architecture

```
Microservices → Kafka (topic: microservices-logs)
                   ↓
              Log-Service (Spring Boot)
                   ↓
              Elasticsearch
                   ↓
              Kibana (Interface web)
```

## 🎯 Fonctionnalités

### Log-Service fait :
1. **Écoute Kafka** : Topic `microservices-logs`
2. **Parse les logs** : JSON → LogEntry
3. **Stocke dans Elasticsearch** : Indexation automatique
4. **API REST** : Pour requêtes custom si besoin

### Kibana te permet de :
- 📊 Visualiser tous les logs dans une interface web
- 🔍 Filtrer par service, niveau, date
- 📈 Créer des dashboards et graphiques
- ⚠️ Configurer des alertes
- 🔎 Recherche full-text dans tous les logs

## 🚀 Utilisation

### En local :

```bash
# Démarrer toute la stack
docker-compose up -d

# Accéder à Kibana
http://localhost:5601
```

### Dans Kibana :

1. **Créer un index pattern** :
   - Aller dans Management → Index Patterns
   - Créer pattern : `microservices-logs*`
   - Sélectionner `@timestamp` comme champ de temps

2. **Visualiser les logs** :
   - Discover → Voir tous les logs en temps réel
   - Filtrer par `service.keyword`, `level.keyword`, etc.

3. **Créer un dashboard** :
   - Visualize → Créer des graphiques
   - Dashboard → Assembler les visualisations

## 📋 Format des logs dans Kafka

Les microservices doivent envoyer des JSON comme :

```json
{
  "service": "User-Service",
  "level": "ERROR",
  "message": "Failed to create user",
  "timestamp": "2025-10-09T12:00:00Z",
  "exception": "NullPointerException: ...",
  "metadata": {
    "userId": "123",
    "endpoint": "/api/users"
  }
}
```

## 🔌 API REST (optionnel)

Si tu ne veux pas utiliser Kibana, tu peux requêter via l'API :

```bash
# Tous les logs
GET http://localhost:8086/api/logs

# Logs d'un service
GET http://localhost:8086/api/logs/service/User-Service

# Logs par niveau
GET http://localhost:8086/api/logs/level/ERROR

# Logs dans un intervalle
GET http://localhost:8086/api/logs/range?start=2025-10-09T00:00:00Z&end=2025-10-09T23:59:59Z
```

## 🛠️ Prochaines étapes

Pour que les autres microservices envoient leurs logs :

1. Ajouter dependency Kafka dans chaque service
2. Configurer un KafkaProducer pour le topic `microservices-logs`
3. Envoyer les logs au lieu de juste les logger

## 📦 Stack complète

- **Kafka** : Port 9092
- **Elasticsearch** : Port 9200
- **Kibana** : Port 5601 ← **Interface web principale**
- **Log-Service** : Port 8086 (API REST optionnelle)

