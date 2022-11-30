import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Jobs from './jobs';
import JobsDetail from './jobs-detail';
import JobsUpdate from './jobs-update';
import JobsDeleteDialog from './jobs-delete-dialog';

const JobsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Jobs />} />
    <Route path="new" element={<JobsUpdate />} />
    <Route path=":id">
      <Route index element={<JobsDetail />} />
      <Route path="edit" element={<JobsUpdate />} />
      <Route path="delete" element={<JobsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobsRoutes;
