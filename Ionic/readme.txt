https://ionicframework.com/getting-started#cli

#Primeiro instalar o Nodejs.

#Para instalar
npm install -g ionic cordova


#Para criar uma aplicação nova
ionic start ceiotApp blank

cd ceiotApp

#Iniciar a aplicação
ionic serve

#Criar novas páginas
ionic generate

#Selecionar
#  page 
#  component 
#  service 
#  module 
#  class 
#  directive 
#  guard
# 
#  p. ex:
  
#ionic generate page pages/NodeList


curl -X POST -d '{"user_id" : "jack", "text" : "Ahoy!"}' \  'https://monitoramento-container.firebaseapp.com/container.json'

curl -X POST -d '{"name": "aaa"}' https://firestore.googleapis.com/v1beta1/ceiot-android/nodes

curl -X POST -d '{"name": "aaa"}' https://firestore.googleapis.com/v1beta1/monitoramento-container/container


POST https://firestore.googleapis.com/v1beta1/ceiot-android/nodes?key={YOUR_API_KEY}
 {"name": "aaa"}
 