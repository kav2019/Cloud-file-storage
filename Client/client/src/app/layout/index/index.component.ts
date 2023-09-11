import { Component } from '@angular/core';
import { User } from "../../model/User";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent {
  user: User = {
  id: 1,
  email: '1@mail.ru',
  username: '1 name'
};

}
