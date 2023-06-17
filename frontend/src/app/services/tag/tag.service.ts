import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, Subject, of } from "rxjs";
import { Tag } from "src/app/models/Tag";

@Injectable({
  providedIn: "root",
})
export class TagService {
  public allTagsResults: Observable<Tag[]>;
  public hostAdress: string = "http://localhost:3000/";
  public subject = new Subject<HttpErrorResponse>();

  constructor(private http: HttpClient) {}

  public onErrorOccurrs(): Observable<HttpErrorResponse> {
    return this.subject.asObservable();
  }

  public getAllTags(): Observable<Tag[]> {
    const url = this.hostAdress + "tag";

    this.allTagsResults = new Observable((observer) => {
      this.http.get<{ tags: Tag[] }>(url).subscribe(
        (response) => {
          const tagsFromResponse = response.tags;

          observer.next(tagsFromResponse);
        },
        (err) => this.handleException(err)
      );
    });

    return this.allTagsResults;
  }

  public handleException(err: HttpErrorResponse) {
    this.subject.next(err);
  }
}
