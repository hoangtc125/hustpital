import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LichHen from './lich-hen';
import LichHenDetail from './lich-hen-detail';
import LichHenUpdate from './lich-hen-update';
import LichHenDeleteDialog from './lich-hen-delete-dialog';

const LichHenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LichHen />} />
    <Route path="new" element={<LichHenUpdate />} />
    <Route path=":id">
      <Route index element={<LichHenDetail />} />
      <Route path="edit" element={<LichHenUpdate />} />
      <Route path="delete" element={<LichHenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LichHenRoutes;
