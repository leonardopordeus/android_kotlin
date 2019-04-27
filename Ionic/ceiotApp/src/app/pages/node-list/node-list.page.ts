import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-node-list',
  templateUrl: './node-list.page.html',
  styleUrls: ['./node-list.page.scss'],
})
export class NodeListPage implements OnInit {

  nodeList: Object[];
  constructor(public httpClient: HttpClient) { }

  ngOnInit() {
    this.httpClient.get('https://ceiot-android-app.firebaseio.com/nodes.json')
    .subscribe((response) => {
        console.log(response);        
        this.firebasetoArray(response);
    });
  }

  firebasetoArray(object) {
    this.nodeList = [];

    Object.keys(object).forEach((key) => {
      this.nodeList.push(object[key]);
    });
    console.log(this.nodeList);        
};
}
