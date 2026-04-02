import React from 'react';
import DistributionChart from './DistributionChart';

export default function ProfessorCard({ professor }) {
  return (
    <div className="soft-card prof-card">
      <div className="section-header" style={{ marginBottom: 18 }}>
        <h3 className="section-title" style={{ fontSize: '1.35rem' }}>{professor.name}</h3>
        <p className="section-subtitle">Taught {professor.timesTaught} time(s)</p>
      </div>

      <div>
        <h4 className="block-title">Professor distribution</h4>
        <DistributionChart distribution={professor.distribution} />
      </div>

      <div className="spacer-top">
        <h4 className="block-title">Past offerings</h4>
        <div className="offering-list">
          {(professor.offerings || []).map((offering, index) => (
            <div
              className="offering-item"
              key={`${professor.name}-${offering.term}-${offering.section}-${index}`}
            >
              <div className="row-between" style={{ alignItems: 'flex-start' }}>
                <div style={{ fontWeight: 700 }}>
                  {offering.term} · Section {offering.section}
                </div>
                <div className="offering-badges">
                  <span className="outline-badge">Median: {offering.median ?? '—'}</span>
                  <span className="outline-badge">Mode: {offering.mode ?? '—'}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
