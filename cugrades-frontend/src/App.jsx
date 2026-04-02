import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import SearchPage from './pages/SearchPage';
import SubjectCoursesPage from './pages/SubjectCoursesPage';
import CourseDetailPage from './pages/CourseDetailPage';
import Footer from './components/Footer';

export default function App() {
  return (
    <div className="app-shell">
      <div className="app-content">
        <Routes>
          <Route path="/" element={<SearchPage />} />
          <Route path="/subjects/:subjectCode" element={<SubjectCoursesPage />} />
          <Route path="/subjects/:subjectCode/courses/:courseCode" element={<CourseDetailPage />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>

      <Footer />
    </div>
  );
}
