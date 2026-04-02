import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { fetchJson } from '../api';
import StatPill from '../components/StatPill';
import ProfessorCard from '../components/ProfessorCard';

function point12GpaToLetter(gpa) {
  if (gpa == null) return "—";

  let letter = "";

  if (gpa >= 12) letter = "A+";
  else if (gpa >= 11) letter = "A";
  else if (gpa >= 10) letter = "A-";
  else if (gpa >= 9) letter = "B+";
  else if (gpa >= 8) letter = "B";
  else if (gpa >= 7) letter = "B-";
  else if (gpa >= 6) letter = "C+";
  else if (gpa >= 5) letter = "C";
  else if (gpa >= 4) letter = "C-";
  else if (gpa >= 3) letter = "D+";
  else if (gpa >= 2) letter = "D";
  else if (gpa >= 1) letter = "D-";
  else letter = "F";

  return `${letter}(${Number(gpa).toFixed(0)})`;
}

export default function CourseDetailPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const { subjectCode, courseCode } = useParams();
  const [details, setDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const subject = location.state?.subject || { code: subjectCode?.toUpperCase(), name: subjectCode?.toUpperCase() };
  const course = location.state?.course || { courseCode, title: '' };

  useEffect(() => {
    let mounted = true;

    async function loadDetails() {
      try {
        setLoading(true);
        setError('');
        const data = await fetchJson(
          `/api/courses/${encodeURIComponent(subjectCode)}/${encodeURIComponent(courseCode)}/details`
        );
        if (mounted) setDetails(data);
      } catch (err) {
        if (mounted) setError(err.message || 'Could not load course details.');
      } finally {
        if (mounted) setLoading(false);
      }
    }

    loadDetails();
    return () => {
      mounted = false;
    };
  }, [subjectCode, courseCode]);

  return (
    <div className="page-shell layout-section">
      <div className="container">
        <div className="section-header">
          <button className="back-button" onClick={() => navigate(`/subjects/${subject.code}`, { state: { subject } })}>
            ← Back to course list
          </button>
          <h1 className="section-title">{subject.code} {course.courseCode}</h1>
          <p className="section-subtitle">{details?.title || course.title}</p>
        </div>

        {loading && <div className="loading-text">Loading detailed history...</div>}
        {error && <div className="error-box">{error}</div>}

        {details && (
          <>
            <div className="stat-grid detail" style={{ marginBottom: 28 }}>
              <StatPill label="Subject" value={details.subject} />
              <StatPill label="Course" value={details.courseCode} />
              <StatPill label="Overall Median" value={point12GpaToLetter(details.overallStats?.median)} />
              <StatPill label="Overall Mode" value={point12GpaToLetter(details.overallStats?.mode)} />
            </div>

            <div className="section-header">
              <h2 className="section-title" style={{ fontSize: '2rem' }}>Past professors</h2>
              <p className="section-subtitle">Each card shows a professor aggregate plus their past offerings.</p>
            </div>

            <div className="grid prof-grid">
              {(details.professors || []).map((professor, index) => (
                <ProfessorCard key={`${professor.name}-${index}`} professor={professor} />
              ))}
            </div>
          </>
        )}
      </div>
    </div>
  );
}
