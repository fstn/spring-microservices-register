package com.microservices.model.application;

/**
 *
 */
public class ApplicationInsideStackTrace {
        /**
         * Application name
         * <p>
         * Example world, fr, eur ..
         */
        private String id;
        /**
         * Application context path
         * <p>
         * Example /id
         */
        private String path;
        /**
         * Parent id name
         * <p>
         * Example world, fr, eur ..
         */
        private String parentApp;
        /**
         * Hostname
         * <p>
         * Example localhost
         */
        private String hostName;
        /**
         * Application port
         */
        private Integer port;
        /**
         * Application instanceId (use if multiple service deployed with same instanceId)
         */
        private String instanceId;

        public ApplicationInsideStackTrace(Application app) {
            this.id = app.getId();
            this.hostName = app.getHostName();
            this.instanceId = app.getInstanceId();
            this.parentApp = app.getParentApp();
            this.path = app.getPath();
            this.port = app.getPort();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getParentApp() {
            return parentApp;
        }

        public void setParentApp(String parentApp) {
            this.parentApp = parentApp;
        }

        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(String instanceId) {
            this.instanceId = instanceId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ApplicationInsideStackTrace)) return false;

            ApplicationInsideStackTrace that = (ApplicationInsideStackTrace) o;

            if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
            if (getPath() != null ? !getPath().equals(that.getPath()) : that.getPath() != null) return false;
            if (getParentApp() != null ? !getParentApp().equals(that.getParentApp()) : that.getParentApp() != null)
                return false;
            if (getHostName() != null ? !getHostName().equals(that.getHostName()) : that.getHostName() != null)
                return false;
            if (getPort() != null ? !getPort().equals(that.getPort()) : that.getPort() != null) return false;
            return getInstanceId() != null ? getInstanceId().equals(that.getInstanceId()) : that.getInstanceId() == null;

        }

        @Override
        public int hashCode() {
            int result = getId() != null ? getId().hashCode() : 0;
            result = 31 * result + (getPath() != null ? getPath().hashCode() : 0);
            result = 31 * result + (getParentApp() != null ? getParentApp().hashCode() : 0);
            result = 31 * result + (getHostName() != null ? getHostName().hashCode() : 0);
            result = 31 * result + (getPort() != null ? getPort().hashCode() : 0);
            result = 31 * result + (getInstanceId() != null ? getInstanceId().hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ApplicationInsideStackTrace{" +
                    "id='" + id + '\'' +
                    ", path='" + path + '\'' +
                    ", parentApp='" + parentApp + '\'' +
                    ", hostName='" + hostName + '\'' +
                    ", port=" + port +
                    ", instanceId='" + instanceId + '\'' +
                    '}';
        }
    }