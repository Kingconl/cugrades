import React, { useMemo } from 'react';
import { normalizeDistribution, totalCount, percent } from '../utils/distribution';

export default function DistributionChart({ distribution, compact = false }) {
  const normalized = useMemo(() => normalizeDistribution(distribution), [distribution]);
  const total = totalCount(normalized);

  return (
    <div className="bar-list">
      {normalized.map((item) => (
        <div key={item.grade} className="bar-row">
          <div>{item.grade}</div>
          <div className="bar-track">
            <div className="bar-fill" style={{ width: `${percent(item.count, total)}%` }} />
          </div>
          <div style={{ textAlign: 'right', color: '#64748b', fontSize: '0.85rem' }}>
            {compact ? item.count : `${item.count} (${percent(item.count, total)}%)`}
          </div>
        </div>
      ))}
    </div>
  );
}
