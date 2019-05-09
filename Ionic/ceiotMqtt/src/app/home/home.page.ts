import { Component, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import * as mqtt from '../../js/mqtt.min';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {


  messageList: any[] = [];

  constructor(
    private router: Router,
    public httpClient: HttpClient) {
  }

  ngOnInit() {
    this.mqttConnect();
  }

  /*
  Configuração para MQTT
  */
  mqttConnect() {
    try {
      
      let that = this; //Referencia para chamar variáveis do angular dentro da       

      //COnfiguração do Broker. (Websockets)
      var options = {
        clientId: 'testCeiot_1',
        connectTimeout: 5000,
        hostname: 'test.mosquitto.org',
        port: 8080,
        path: '/mqtt'
      };
      
      //Conexão
      var client = mqtt.connect(options);
      
      //Se inscreve em um tópico ao se conectar ao broker
      client.on('connect', function () {
        client.subscribe('ceiot', function (err) {
          if (!err) {
            client.publish('ceiot', 'Hello mqtt')
          }
        })
      });

      //Tratamento ao receber mensagem
      client.on('message', function (topic, message) {
            that.receiveMessage(topic, message);
      });

    } catch (e) {
      console.log(e);
    }
  }

  /**
   * Adiciona tópico e mensagem no array messageList
   * @param topic 
   * @param message 
   */
  receiveMessage(topic, message)
  { 
    console.log(message.toString())      
    var obj = {};
    obj['topic'] = topic;
    obj['message'] = message;
    this.messageList.push(obj);
  }

}
