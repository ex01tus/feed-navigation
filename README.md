# Feed with cursor navigation

![](https://img.shields.io/badge/kotlin-Test%20Assignment-important?style=for-the-badge&logo=kotlin)

We have a feed of 10000 string elements saved in an ordered set in Redis.

Implement a service, allowing to get a part of the feed and to navigate it using cursors.
Service responses should also include flags to determine if the previous and/or the next part exists.

Please, use embedded Redis and generate test data on the start on an application.

Do NOT use Spring pagination mechanism. We're interested in you implementing it from scratch.

Good luck!

## Request examples

### Feed beginning

```json
GET /feed?limit=3

{
    "items": [
        "first",
        "second",
        "third"
    ],
    "navigation": {
        "prev": "-1",
        "next": "3",
        "hasPrev": false,
        "hasNext": true,
    }
}
```

### Second feed part

Passing `.navigation.next` as a `next` GET-parameter to scroll forward. Similarly, use `prev` to scroll backwards.

```json
GET /feed?limit=3&next=3

{
    "items": [
        "fourth",
        "fifth",
        "sixth"
    ],
    "navigation": {
        "prev": "2",
        "next": "6",
        "hasPrev": true,
        "hasNext": true,
    }
}
```

### Feed end

```json
GET /feed?limit=3&next=9999

{
    "items": [
        "last"
    ],
    "navigation": {
        "prev": "9998",
        "next": "-1",
        "hasPrev": true,
        "hasNext": false
    }
}
```
