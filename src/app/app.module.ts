import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import { MatToolbarModule } from "@angular/material/toolbar";
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatSidenavModule} from '@angular/material/Sidenav';
import {MatListItemModule} from '@angular/material/ListItem';
import {MatListModule} from '@angular/material/List';


@NgModule({
  declarations: [
    AppComponent,
    AdminTemplateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
     MatToolbarModule,
     MatButtonModule,
     MatIconModule,
     MatMenuModule,
     MatSidenavModule,
     MatListItemModule,
     MatListModule


  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
