import { GRADE_ORDER } from '../constants/grades';

export function normalizeDistribution(items = []) {
  const map = new Map(items.map((item) => [item.grade, Number(item.count ?? 0)]));

  return GRADE_ORDER.map((grade) => ({
    grade,
    count: map.get(grade) ?? 0,
  }));
}

export function totalCount(distribution = []) {
  return distribution.reduce((sum, item) => sum + Number(item.count ?? 0), 0);
}

export function percent(count, total) {
  if (!total) return 0;
  return Math.round((count / total) * 100);
}
