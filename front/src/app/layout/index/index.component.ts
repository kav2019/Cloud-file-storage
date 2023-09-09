import { Component, OnInit } from '@angular/core';
import {User} from '../../models/User';
import {UserService} from '../../service/user.service';
import {NotificationService} from '../../service/notification.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  isPostsLoaded = false;
  // @ts-ignore
  posts: Post[];
  isUserDataLoaded = false;
  // @ts-ignore
  user: User;

  constructor(
              private userService: UserService,
              private notificationService: NotificationService
  ) { }

  ngOnInit(): void {

    this.userService.getCurrentUser()
      .subscribe(data => {
        console.log(data);
        this.user = data;
        this.isUserDataLoaded = true;
      })
  }


}
