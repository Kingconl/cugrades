import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchJson } from '../api';

export default function SearchPage() {
  const navigate = useNavigate();
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    const trimmed = query.trim();

    if (!trimmed) {
      setResults([]);
      setError('');
      return;
    }

    const timer = setTimeout(async () => {
      try {
        setLoading(true);
        setError('');
        const data = await fetchJson(`/api/subjects?query=${encodeURIComponent(trimmed)}`);
        setResults(Array.isArray(data) ? data : []);
      } catch (err) {
        setError(err.message || 'Could not load subjects.');
      } finally {
        setLoading(false);
      }
    }, 300);

    return () => clearTimeout(timer);
  }, [query]);

  return (
    <div className="page-shell hero">
      <div className="hero-container">
        <div style={{ textAlign: 'center', marginBottom: 28 }}>
          <div className="hero-badge">CU Grades</div>
          <h1 className="hero-title">Search by subject</h1>
          <p className="hero-subtitle">In beta and currently only supports COMP(Computer Science).</p>
        </div>

        <div className="card search-box">
          <div className="search-input-wrap">
            <input
              className="search-input"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              placeholder="Search subjects..."
            />
          </div>

          <div className="spacer-top result-list">
            {loading && <div className="loading-text">Searching subjects...</div>}
            {error && <div className="error-box">{error}</div>}
            {!loading && query.trim() && results.length === 0 && !error && (
              <div className="empty-box">No subjects found.</div>
            )}

            {results.map((subject) => (
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
          </div>
        </div>
      </div>
    </div>
  );
}
