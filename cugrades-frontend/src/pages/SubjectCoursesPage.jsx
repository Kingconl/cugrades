import React, { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { fetchJson } from '../api';
import StatPill from '../components/StatPill';
import DistributionChart from '../components/DistributionChart';

const PAGE_SIZE = 6;

function titleFromCode(code) {
  return code?.toUpperCase() || '';
}

function point12GpaToLetter(gpa) {
  if (gpa == null) return '—';

  let letter = '';

  if (gpa >= 12) letter = 'A+';
  else if (gpa >= 11) letter = 'A';
  else if (gpa >= 10) letter = 'A-';
  else if (gpa >= 9) letter = 'B+';
  else if (gpa >= 8) letter = 'B';
  else if (gpa >= 7) letter = 'B-';
  else if (gpa >= 6) letter = 'C+';
  else if (gpa >= 5) letter = 'C';
  else if (gpa >= 4) letter = 'C-';
  else if (gpa >= 3) letter = 'D+';
  else if (gpa >= 2) letter = 'D';
  else if (gpa >= 1) letter = 'D-';
  else letter = 'F';

  return `${letter}(${Number(gpa).toFixed(2)})`;
}

export default function SubjectCoursesPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const { subjectCode } = useParams();

  const [courses, setCourses] = useState([]);
  const [level, setLevel] = useState('');
  const [offset, setOffset] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  const [loading, setLoading] = useState(true);
  const [loadingMore, setLoadingMore] = useState(false);
  const [error, setError] = useState('');

  const sentinelRef = useRef(null);

  const subject = location.state?.subject || {
    code: subjectCode?.toUpperCase(),
    name: titleFromCode(subjectCode),
  };

  useEffect(() => {
    setCourses([]);
    setOffset(0);
    setHasMore(true);
    setError('');
  }, [subjectCode, level]);

  useEffect(() => {
    let mounted = true;

    async function loadCourses() {
      try {
        if (offset === 0) {
          setLoading(true);
        } else {
          setLoadingMore(true);
        }

        setError('');

        const params = new URLSearchParams();
        if (level) params.set('level', level);
        params.set('limit', PAGE_SIZE);
        params.set('offset', offset);

        const url = `/api/courses/${encodeURIComponent(subjectCode)}/courses?${params.toString()}`;
        const data = await fetchJson(url);
        const newCourses = Array.isArray(data) ? data : [];

        if (!mounted) return;

        if (offset === 0) {
          setCourses(newCourses);
        } else {
          setCourses((prev) => [...prev, ...newCourses]);
        }

        setHasMore(newCourses.length === PAGE_SIZE);
      } catch (err) {
        if (mounted) {
          setError(err.message || 'Could not load courses.');
        }
      } finally {
        if (mounted) {
          setLoading(false);
          setLoadingMore(false);
        }
      }
    }

    if (subjectCode) {
      loadCourses();
    }

    return () => {
      mounted = false;
    };
  }, [subjectCode, level, offset]);

  useEffect(() => {
    const node = sentinelRef.current;
    if (!node) return;
    if (loading || loadingMore || !hasMore) return;

    const observer = new IntersectionObserver(
      (entries) => {
        const first = entries[0];
        if (first.isIntersecting) {
          setOffset((prev) => prev + PAGE_SIZE);
        }
      },
      {
        root: null,
        rootMargin: '250px',
        threshold: 0,
      }
    );

    observer.observe(node);

    return () => observer.disconnect();
  }, [loading, loadingMore, hasMore, courses.length]);

  return (
    <div className="page-shell layout-section">
      <div className="container">
        <div className="section-header">
          <button className="back-button" onClick={() => navigate('/')}>
            ← Back to search
          </button>

          <div
            style={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'space-between',
              gap: '16px',
              flexWrap: 'wrap',
            }}
          >
            <h1 className="section-title" style={{ margin: 0 }}>
              {subject.code} courses
            </h1>

            <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
              <label htmlFor="level-filter" className="muted">
                Level
              </label>
              <select
                id="level-filter"
                value={level}
                onChange={(e) => setLevel(e.target.value)}
                style={{
                  padding: '8px 12px',
                  borderRadius: '10px',
                  border: '1px solid #d1d5db',
                  background: 'white',
                  fontSize: '0.95rem',
                  cursor: 'pointer',
                }}
              >
                <option value="">All</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
              </select>
            </div>
          </div>

          <p className="section-subtitle">{subject.name}</p>
        </div>

        {loading && <div className="loading-text">Loading course summaries...</div>}
        {error && <div className="error-box">{error}</div>}

        <div className="grid courses-grid">
          {courses.map((course) => (
            <div className="soft-card course-card" key={`${course.subject}-${course.courseCode}`}>
              <div className="row-between" style={{ alignItems: 'flex-start', marginBottom: 18 }}>
                <div>
                  <div className="course-code">
                    {course.subject} {course.courseCode}
                  </div>
                  <div className="muted">{course.title}</div>
                </div>
              </div>

              <div className="stat-grid">
                <StatPill label="Average" value={point12GpaToLetter(course.overallStats?.mean)} />
                <StatPill label="Mode" value={point12GpaToLetter(course.overallStats?.mode)} />
                <StatPill label="Students" value={course.studentCount} />
              </div>

              <div className="spacer-top">
                <h4 className="block-title">Distribution</h4>
                <DistributionChart distribution={course.distribution} compact />
              </div>

              <div className="spacer-top">
                <button
                  className="primary-button course-button"
                  onClick={() =>
                    navigate(`/subjects/${subjectCode}/courses/${course.courseCode}`, {
                      state: { subject, course },
                    })
                  }
                >
                  View detailed course history
                </button>
              </div>
            </div>
          ))}
        </div>

        {!loading && !error && courses.length === 0 && (
          <div className="loading-text">No courses found.</div>
        )}

        {loadingMore && (
          <div style={{ marginTop: '24px', textAlign: 'center' }} className="muted">
            Loading more courses...
          </div>
        )}

        {!loading && hasMore && <div ref={sentinelRef} style={{ height: '40px' }} />}

        {!loading && !hasMore && courses.length > 0 && (
          <div style={{ marginTop: '24px', textAlign: 'center' }} className="muted">
            No more courses.
          </div>
        )}
      </div>
    </div>
  );
}
