docker login

docker tag abacusflow-web:0.0.1 brucewang5638/abacusflow-web:0.0.1
docker push brucewang5638/abacusflow-web:0.0.1

docker tag abacusflow-server:0.0.1 brucewang5638/abacusflow-server:0.0.1
docker push brucewang5638/abacusflow-server:0.0.1

docker compose -f docker-compose-prod.yml down
docker compose -f docker-compose-prod.yml down xxx xxx

docker volume rm abacusflow_postgres_data

docker compose -f docker-compose-prod.yml pull
docker compose -f docker-compose-prod.yml up -d --remove-orphans

