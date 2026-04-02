# Carleton Grades Frontend

This is a Vite + React frontend for your Carleton grades backend.

## Routes used

- `GET /api/subjects?query=COMP`
- `GET /api/courses/COMP/courses`
- `GET /api/courses/COMP/2406/details`

## Run locally

1. Start your Spring Boot backend on port `8080`
2. In this folder run:

```bash
npm install
npm run dev
```

The Vite dev server proxies `/api/*` requests to `http://localhost:8080`.

## Expected backend shapes

### Subject search
```json
[
  { "id": 1, "code": "COMP", "name": "Computer Science" }
]
```

### Subject course list
```json
[
  {
    "subject": "COMP",
    "courseCode": "2406",
    "title": "Fundamentals of Web Applications",
    "studentCount": 120,
    "overallStats": { "median": 78.0, "mode": 80.0 },
    "distribution": [
      { "grade": "A+", "count": 10 },
      { "grade": "A", "count": 20 }
    ]
  }
]
```

### Detailed course view
```json
{
  "subject": "COMP",
  "courseCode": "2406",
  "title": "Fundamentals of Web Applications",
  "overallStats": { "median": 78.0, "mode": 80.0 },
  "professors": [
    {
      "name": "Jane Smith",
      "timesTaught": 4,
      "distribution": [
        { "grade": "A+", "count": 8 },
        { "grade": "A", "count": 17 }
      ],
      "offerings": [
        { "term": "2024F", "section": "A", "median": 80.0, "mode": 82.0 }
      ]
    }
  ]
}
```
