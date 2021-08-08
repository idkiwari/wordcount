import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
    input: String = "";
    output: String = "";

    constructor(public http:HttpClient) {}

    exec(){
      this.http.post('/execute', {value: this.input}).subscribe(res => {
        this.output = String(res);
      });
    }

}
