import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchJson } from '../api';

export default function SearchPage() {
  const navigate = useNavigate();
  const [query, setQuery] = useState('');
  const [subjectResults, setSubjectResults] = useState([]);
  const [courseResults, setCourseResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    const trimmed = query.trim();

    if (!trimmed) {
      setSubjectResults([]);
      setCourseResults([]);
      setError('');
      return;
    }

    const timer = setTimeout(async () => {
      try {
        setLoading(true);
        setError('');

        const [subjectsData, coursesData] = await Promise.all([
          fetchJson(`/api/subjects?query=${encodeURIComponent(trimmed)}`),
          fetchJson(`/api/courses?query=${encodeURIComponent(trimmed)}`),
        ]);

        setSubjectResults(Array.isArray(subjectsData) ? subjectsData : []);
        setCourseResults(Array.isArray(coursesData) ? coursesData : []);
      } catch (err) {
        setError(err.message || 'Could not load search results.');
        setSubjectResults([]);
        setCourseResults([]);
      } finally {
        setLoading(false);
      }
    }, 300);

    return () => clearTimeout(timer);
  }, [query]);

  const hasNoResults =
    !loading &&
    query.trim() &&
    subjectResults.length === 0 &&
    courseResults.length === 0 &&
    !error;

  return (
    <div className="page-shell hero">
      <div className="hero-container">
        <div style={{ textAlign: 'center', marginBottom: 28 }}>
          <div className="hero-badge">CU Grades</div>
          <h1 className="hero-title">Search by subject or course</h1>
          <p className="hero-subtitle">
            In beta and currently only supports COMP (Computer Science).
          </p>
        </div>

        <div className="card search-box">
          <div className="search-input-wrap">
            <input
              className="search-input"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              placeholder="Search subjects or course numbers..."
            />
          </div>

          <div className="spacer-top result-list">
            {loading && <div className="loading-text">Searching...</div>}
            {error && <div className="error-box">{error}</div>}
            {hasNoResults && <div className="empty-box">No results found.</div>}

            {subjectResults.length > 0 && (
              <>
                <div className="muted" style={{ marginBottom: 10 }}>Subjects</div>
                {subjectResults.map((subject) => (
                  <button
                    key={subject.id}
                    className="subject-button"
                    onClick={() => navigate(`/subjects/${subject.code}`, { state: { subject } })}
                  >
                    <div className="subject-row">
                      <div>
                        <div className="subject-code">{subject.code}</div>
                        <div className="muted">{subject.name}</div>
                      </div>
                      <span className="badge">Select</span>
                    </div>
                  </button>
                ))}
              </>
            )}

            {courseResults.length > 0 && (
              <>
                <div className="muted" style={{ margin: '16px 0 10px' }}>Courses</div>
                {courseResults.map((course) => (
                  <button
                    key={`${course.subject}-${course.courseN}`}
                    className="subject-button"
                    onClick={() =>
                      navigate(`/subjects/${course.subject}/courses/${course.courseNumber}`)
                    }
                  >
                    <div className="subject-row">
                      <div>
                        <div className="subject-code">
                          {course.subject} {course.courseNumber}
                        </div>
                        <div className="muted">{course.title}</div>
                      </div>
                      <span className="badge">Open</span>
                    </div>
                  </button>
                ))}
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
