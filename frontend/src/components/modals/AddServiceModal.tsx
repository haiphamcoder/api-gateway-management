import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { useTranslation } from 'react-i18next';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { servicesApi } from '../../services/api';
import { Dialog, Transition } from '@headlessui/react';
import { XMarkIcon } from '@heroicons/react/24/outline';
import toast from 'react-hot-toast';

interface AddServiceModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

const ServiceSchema = Yup.object().shape({
  name: Yup.string()
    .matches(/^[A-Za-z0-9.-]+$/, 'Service name must contain only alphanumeric characters, dots, and hyphens')
    .required('Service name is required'),
  url: Yup.string()
    .url('Please enter a valid URL')
    .required('URL is required'),
  connectTimeout: Yup.number()
    .min(1, 'Timeout must be at least 1ms')
    .max(120000, 'Timeout must not exceed 120000ms')
    .required('Connect timeout is required'),
  writeTimeout: Yup.number()
    .min(1, 'Timeout must be at least 1ms')
    .max(120000, 'Timeout must not exceed 120000ms')
    .required('Write timeout is required'),
  readTimeout: Yup.number()
    .min(1, 'Timeout must be at least 1ms')
    .max(120000, 'Timeout must not exceed 120000ms')
    .required('Read timeout is required'),
  retries: Yup.number()
    .min(0, 'Retries must be non-negative')
    .max(10, 'Retries must not exceed 10')
    .required('Retries is required'),
});

export const AddServiceModal: React.FC<AddServiceModalProps> = ({
  isOpen,
  onClose,
  onSuccess,
}) => {
  const { t } = useTranslation();
  const queryClient = useQueryClient();

  const createServiceMutation = useMutation({
    mutationFn: servicesApi.createService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['services'] });
      toast.success(t('services.serviceCreated'));
      onSuccess();
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || 'Failed to create service');
    },
  });

  const formik = useFormik({
    initialValues: {
      name: '',
      url: '',
      connectTimeout: 30000,
      writeTimeout: 30000,
      readTimeout: 30000,
      retries: 5,
    },
    validationSchema: ServiceSchema,
    onSubmit: (values) => {
      createServiceMutation.mutate(values);
    },
  });

  const handleClose = () => {
    formik.resetForm();
    onClose();
  };

  return (
    <Transition appear show={isOpen} as={React.Fragment}>
      <Dialog as="div" className="relative z-10" onClose={handleClose}>
        <Transition
          as={React.Fragment}
          enter="ease-out duration-300"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in duration-200"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-black bg-opacity-25" />
        </Transition>

        <div className="fixed inset-0 overflow-y-auto">
          <div className="flex min-h-full items-center justify-center p-4 text-center">
            <Transition
              as={React.Fragment}
              enter="ease-out duration-300"
              enterFrom="opacity-0 scale-95"
              enterTo="opacity-100 scale-100"
              leave="ease-in duration-200"
              leaveFrom="opacity-100 scale-100"
              leaveTo="opacity-0 scale-95"
            >
              <div className="w-full max-w-md transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">
                <div className="flex items-center justify-between mb-4">
                  <h3 className="text-lg font-medium leading-6 text-gray-900">
                    {t('services.addService')}
                  </h3>
                  <button
                    type="button"
                    className="text-gray-400 hover:text-gray-600"
                    onClick={handleClose}
                  >
                    <XMarkIcon className="h-6 w-6" />
                  </button>
                </div>

                <form onSubmit={formik.handleSubmit} className="space-y-4">
                  <div>
                    <label htmlFor="name" className="block text-sm font-medium text-gray-700">
                      {t('services.serviceName')}
                    </label>
                    <input
                      type="text"
                      id="name"
                      name="name"
                      value={formik.values.name}
                      onChange={formik.handleChange}
                      onBlur={formik.handleBlur}
                      className={`mt-1 block w-full rounded-md border ${
                        formik.touched.name && formik.errors.name
                          ? 'border-red-300'
                          : 'border-gray-300'
                      } shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm`}
                      placeholder="my-service"
                    />
                    {formik.touched.name && formik.errors.name && (
                      <p className="mt-1 text-sm text-red-600">{formik.errors.name}</p>
                    )}
                  </div>

                  <div>
                    <label htmlFor="url" className="block text-sm font-medium text-gray-700">
                      {t('services.backendUrl')}
                    </label>
                    <input
                      type="url"
                      id="url"
                      name="url"
                      value={formik.values.url}
                      onChange={formik.handleChange}
                      onBlur={formik.handleBlur}
                      className={`mt-1 block w-full rounded-md border ${
                        formik.touched.url && formik.errors.url
                          ? 'border-red-300'
                          : 'border-gray-300'
                      } shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm`}
                      placeholder="https://api.example.com"
                    />
                    {formik.touched.url && formik.errors.url && (
                      <p className="mt-1 text-sm text-red-600">{formik.errors.url}</p>
                    )}
                  </div>

                  <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
                    <div>
                      <label htmlFor="connectTimeout" className="block text-sm font-medium text-gray-700">
                        {t('services.connectTimeout')}
                      </label>
                      <input
                        type="number"
                        id="connectTimeout"
                        name="connectTimeout"
                        value={formik.values.connectTimeout}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        className={`mt-1 block w-full rounded-md border ${
                          formik.touched.connectTimeout && formik.errors.connectTimeout
                            ? 'border-red-300'
                            : 'border-gray-300'
                        } shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm`}
                      />
                      {formik.touched.connectTimeout && formik.errors.connectTimeout && (
                        <p className="mt-1 text-sm text-red-600">{formik.errors.connectTimeout}</p>
                      )}
                    </div>

                    <div>
                      <label htmlFor="writeTimeout" className="block text-sm font-medium text-gray-700">
                        {t('services.writeTimeout')}
                      </label>
                      <input
                        type="number"
                        id="writeTimeout"
                        name="writeTimeout"
                        value={formik.values.writeTimeout}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        className={`mt-1 block w-full rounded-md border ${
                          formik.touched.writeTimeout && formik.errors.writeTimeout
                            ? 'border-red-300'
                            : 'border-gray-300'
                        } shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm`}
                      />
                      {formik.touched.writeTimeout && formik.errors.writeTimeout && (
                        <p className="mt-1 text-sm text-red-600">{formik.errors.writeTimeout}</p>
                      )}
                    </div>

                    <div>
                      <label htmlFor="readTimeout" className="block text-sm font-medium text-gray-700">
                        {t('services.readTimeout')}
                      </label>
                      <input
                        type="number"
                        id="readTimeout"
                        name="readTimeout"
                        value={formik.values.readTimeout}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        className={`mt-1 block w-full rounded-md border ${
                          formik.touched.readTimeout && formik.errors.readTimeout
                            ? 'border-red-300'
                            : 'border-gray-300'
                        } shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm`}
                      />
                      {formik.touched.readTimeout && formik.errors.readTimeout && (
                        <p className="mt-1 text-sm text-red-600">{formik.errors.readTimeout}</p>
                      )}
                    </div>
                  </div>

                  <div>
                    <label htmlFor="retries" className="block text-sm font-medium text-gray-700">
                      {t('services.retries')}
                    </label>
                    <input
                      type="number"
                      id="retries"
                      name="retries"
                      value={formik.values.retries}
                      onChange={formik.handleChange}
                      onBlur={formik.handleBlur}
                      className={`mt-1 block w-full rounded-md border ${
                        formik.touched.retries && formik.errors.retries
                          ? 'border-red-300'
                          : 'border-gray-300'
                      } shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm`}
                    />
                    {formik.touched.retries && formik.errors.retries && (
                      <p className="mt-1 text-sm text-red-600">{formik.errors.retries}</p>
                    )}
                  </div>

                  <div className="flex justify-end space-x-3 pt-4">
                    <button
                      type="button"
                      onClick={handleClose}
                      className="btn-secondary"
                    >
                      {t('common.cancel')}
                    </button>
                    <button
                      type="submit"
                      disabled={createServiceMutation.isPending}
                      className="btn-primary disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                      {createServiceMutation.isPending ? 'Creating...' : t('common.create')}
                    </button>
                  </div>
                </form>
              </div>
            </Transition>
          </div>
        </div>
      </Dialog>
    </Transition>
  );
};
