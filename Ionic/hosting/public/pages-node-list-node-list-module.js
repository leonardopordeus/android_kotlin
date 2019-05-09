(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["pages-node-list-node-list-module"],{

/***/ "./src/app/pages/node-list/node-list.module.ts":
/*!*****************************************************!*\
  !*** ./src/app/pages/node-list/node-list.module.ts ***!
  \*****************************************************/
/*! exports provided: NodeListPageModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NodeListPageModule", function() { return NodeListPageModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _ionic_angular__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @ionic/angular */ "./node_modules/@ionic/angular/dist/fesm5.js");
/* harmony import */ var _node_list_page__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./node-list.page */ "./src/app/pages/node-list/node-list.page.ts");







var routes = [
    {
        path: '',
        component: _node_list_page__WEBPACK_IMPORTED_MODULE_6__["NodeListPage"]
    }
];
var NodeListPageModule = /** @class */ (function () {
    function NodeListPageModule() {
    }
    NodeListPageModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_2__["CommonModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormsModule"],
                _ionic_angular__WEBPACK_IMPORTED_MODULE_5__["IonicModule"],
                _angular_router__WEBPACK_IMPORTED_MODULE_4__["RouterModule"].forChild(routes)
            ],
            declarations: [_node_list_page__WEBPACK_IMPORTED_MODULE_6__["NodeListPage"]]
        })
    ], NodeListPageModule);
    return NodeListPageModule;
}());



/***/ }),

/***/ "./src/app/pages/node-list/node-list.page.html":
/*!*****************************************************!*\
  !*** ./src/app/pages/node-list/node-list.page.html ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<ion-header>\n  <ion-toolbar>\n      <ion-buttons slot=\"start\">\n          <ion-back-button></ion-back-button>\n      </ion-buttons>\n    <ion-title>NodeList</ion-title>\n  </ion-toolbar>\n</ion-header>\n\n<ion-content>\n  <ion-list>\n    <ion-item *ngFor=\"let node of nodeList\">\n      <div>\n          <p>{{node.name}}</p>\n          <a>{{node.description}}</a>\n      </div>\n    </ion-item>\n  </ion-list>\n</ion-content>\n"

/***/ }),

/***/ "./src/app/pages/node-list/node-list.page.scss":
/*!*****************************************************!*\
  !*** ./src/app/pages/node-list/node-list.page.scss ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3BhZ2VzL25vZGUtbGlzdC9ub2RlLWxpc3QucGFnZS5zY3NzIn0= */"

/***/ }),

/***/ "./src/app/pages/node-list/node-list.page.ts":
/*!***************************************************!*\
  !*** ./src/app/pages/node-list/node-list.page.ts ***!
  \***************************************************/
/*! exports provided: NodeListPage */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NodeListPage", function() { return NodeListPage; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");



var NodeListPage = /** @class */ (function () {
    function NodeListPage(httpClient) {
        this.httpClient = httpClient;
    }
    NodeListPage.prototype.ngOnInit = function () {
        var _this = this;
        this.httpClient.get('https://ceiot-android-app.firebaseio.com/nodes.json')
            .subscribe(function (response) {
            console.log(response);
            _this.firebasetoArray(response);
        });
    };
    NodeListPage.prototype.firebasetoArray = function (object) {
        var _this = this;
        this.nodeList = [];
        Object.keys(object).forEach(function (key) {
            _this.nodeList.push(object[key]);
        });
        console.log(this.nodeList);
    };
    ;
    NodeListPage = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-node-list',
            template: __webpack_require__(/*! ./node-list.page.html */ "./src/app/pages/node-list/node-list.page.html"),
            styles: [__webpack_require__(/*! ./node-list.page.scss */ "./src/app/pages/node-list/node-list.page.scss")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]])
    ], NodeListPage);
    return NodeListPage;
}());



/***/ })

}]);
//# sourceMappingURL=pages-node-list-node-list-module.js.map