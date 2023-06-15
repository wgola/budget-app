import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { Tag } from "src/app/models/Tag";

@Injectable({
  providedIn: "root",
})
export class TagService {
  constructor() {}

  public getAllTags(): Observable<Tag[]> {
    let tag1: Tag = { name: "shop1" };
    let tag2: Tag = { name: "shop2" };
    let tag3: Tag = { name: "electronics" };
    let tag4: Tag = { name: "grocieries" };
    let tags = [tag1, tag2, tag3, tag4];

    return of(tags);
  }
}
